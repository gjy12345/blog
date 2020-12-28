package cn.gjy.blog.framework.database;

import cn.gjy.blog.framework.annotation.Alias;
import cn.gjy.blog.framework.annotation.UseHump;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.log.SimpleLog;
import cn.gjy.blog.framework.tool.ClassTool;
import cn.gjy.blog.model.DetailedArticle;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanAssignment {

    private static final Map<String,BeanFieldsInfo> infoMap=new ConcurrentHashMap<>();

    private static final SimpleLog log=SimpleLog.log(BeanAssignment.class);

    private static final Set<Class<?>> basicDataClassSet=new HashSet<>();

    private static final Map<String,Class<?>> basicObjectMap=new HashMap<>();

    public static Set<Class<?>> getBasicDataClassSet() {
        return basicDataClassSet;
    }

    static {
        basicDataClassSet.add(Integer.class);
        basicDataClassSet.add(int.class);
        basicDataClassSet.add(String.class);
        basicDataClassSet.add(Long.class);
        basicDataClassSet.add(long.class);
        basicDataClassSet.add(short.class);
        basicDataClassSet.add(Short.class);
        basicDataClassSet.add(Double.class);
        basicDataClassSet.add(double.class);
        basicDataClassSet.add(float.class);
        basicDataClassSet.add(Float.class);
        basicDataClassSet.add(byte.class);
        basicDataClassSet.add(Byte.class);
        basicDataClassSet.add(Date.class);
        basicObjectMap.put(int.class.getName(),Integer.class);
        basicObjectMap.put(double.class.getName(),Double.class);
        basicObjectMap.put(long.class.getName(),Long.class);
        basicObjectMap.put(short.class.getName(),Short.class);
        basicObjectMap.put(float.class.getName(),Float.class);
        basicObjectMap.put(byte.class.getName(),Byte.class);
        basicObjectMap.put(boolean.class.getName(),Boolean.class);
    }

    public static <T> T assignmentBean(ResultSet resultSet,Class<T> tClass) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(!resultSet.next()){
            return null;
        }
        if (!resultSet.isLast()) {
            if(FrameworkConfig.dbTooMuchResultException){
                throw new RuntimeException("返回结果不唯一!");
            }
            log.e("有多个结果被丢弃，因为当前转换模式为:转换单个对象");
        }
        return assignmentBean(resultSet,tClass,getBeanFields(tClass.getName()),resultSet.getMetaData());
    }

    public static <T> List<T> assignmentBeanList(ResultSet resultSet, Class<T> tClass) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<T> list=new ArrayList<>();
        while (resultSet.next()){
            list.add(assignmentBean(resultSet,tClass,getBeanFields(tClass.getName()),resultSet.getMetaData()));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] assignmentBeanArray(ResultSet resultSet, Class<T> tClass) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<T> list=assignmentBeanList(resultSet,tClass);
        T[] arr= (T[]) Array.newInstance(tClass,list.size());
        for (int i = 0; i < arr.length; i++) {
            Array.set(arr,i,list.get(i));
        }
        return arr;
    }


    @SuppressWarnings("unchecked")
    private static  <T> T assignmentBean(ResultSet resultSet,Class<T> tClass,BeanFieldsInfo info,ResultSetMetaData metaData) throws SQLException, IllegalAccessException, InstantiationException {
        if(Map.class.isAssignableFrom(tClass)){
            return (T) valueToMap(resultSet,resultSet.getMetaData(), (Class<? extends Map<String, Object>>) tClass);
        }else if(getBasicDataClassSet().contains(tClass)){
            //普通的数据类型
            if(metaData.getColumnCount()==1){
                Object object = resultSet.getObject(1);
                if (object!=null) {
                    if(object.getClass().equals(tClass)){
                        return (T) object;
                    }else if(object instanceof Number&&Number.class.isAssignableFrom(tClass)||
                            basicDataClassSet.contains(tClass)){
                        Number number= (Number) object;
                        if(tClass==Integer.class||tClass==int.class)
                            return (T) new Integer(number.intValue());
                        else if(tClass==Double.class||tClass==double.class)
                            return (T) new Double(number.doubleValue());
                        else if(tClass==Long.class||tClass==long.class)
                            return (T) new Long(number.longValue());
                        else {
                            log.e("无法将:"+object.getClass().getName()+" 转换到:"+tClass.getName());
                            throw new RuntimeException("无法将:"+object.getClass().getName()+" 转换到:"+tClass.getName());
                        }
                    }else if(tClass==String.class){
                        return (T) object.toString();
                    } else{
                        log.e("无法将: "+object.getClass().getName()+" 转换到:"+tClass.getName());
                        throw new RuntimeException("无法将:"+object.getClass().getName()+" 转换到:"+tClass.getName());
                    }
                }else
                    return null;
            }else
                throw new RuntimeException("结果集列数不唯一  :"+tClass);
        }
        else if(tClass.isInterface()){
            throw new RuntimeException("interface can't instance  :"+tClass);
        }else if(!tClass.getName().startsWith(FrameworkConfig.basePackage)){
            throw new RuntimeException("无法序列化  :"+tClass+" ");
        }
        T t=tClass.newInstance();
        Field field;
        for (int i = 1,l=metaData.getColumnCount(); i <= l; i++) {
            if((field=info.fieldMap.get(metaData.getColumnLabel(i)))!=null){
                int type=metaData.getColumnType(i);
                switch (type){
                    case Types.INTEGER:
                    case Types.SMALLINT:
                    case Types.FLOAT:
                    case Types.DOUBLE:
                    case Types.BIGINT:
                    case Types.TINYINT:
                        TypeParseEngine.parseNumber(type,t,field,resultSet,i);
                        break;
                    case Types.NUMERIC:
                        TypeParseEngine.parseNumeric(type,t,field,resultSet,i);
                        break;
                    case Types.LONGVARCHAR:
                    case Types.LONGNVARCHAR:
                    case Types.CHAR:
                    case Types.VARCHAR:
                        TypeParseEngine.parseString(type,t,field,resultSet,i);
                        break;
                    case Types.BIT:
                        TypeParseEngine.parseBit(type,t,field,resultSet,i);
                        break;
                    case Types.DATE:
                    case Types.TIME:
                    case Types.TIMESTAMP:
                        TypeParseEngine.parseData(type,t,field,resultSet,i);
                        break;
                }
            }
        }
        return t;
    }

    private static Map<String,Object> valueToMap(ResultSet resultSet, ResultSetMetaData metaData,Class<? extends Map<String,Object>> returnClass) throws SQLException, IllegalAccessException, InstantiationException {
        Map<String,Object> map;
        if(returnClass.isInterface()){
            map=new HashMap<>();
        }else {
            map= returnClass.newInstance();
        }
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            map.put(metaData.getColumnLabel(i),resultSet.getObject(i));
        }
        return map;
    }


    private static BeanFieldsInfo getBeanFields(String className) throws ClassNotFoundException {
        if(!infoMap.containsKey(className)){
            infoMap.put(className,createBeanInfo(className));
        }
        return infoMap.get(className);
    }

    //缓存class字段信息
    private synchronized static BeanFieldsInfo createBeanInfo(String className) throws ClassNotFoundException {
        if(infoMap.containsKey(className)){
            return infoMap.get(className);
        }
        //判断是否是基本数据
        Class<?> c;
        if(className.length()<8&&basicObjectMap.containsKey(className)){
            c=basicObjectMap.get(className);
        }else {
            c=Class.forName(className);
        }
        BeanFieldsInfo info=new BeanFieldsInfo();
        info.fieldMap=new HashMap<>();
        Field[] fields = ClassTool.getClassAllFields(c);
        info.classes=new Class[fields.length];
        Alias alias;
        UseHump useHump;
        for (int i = 0,l=fields.length; i < l; i++) {
            fields[i].setAccessible(true);
            info.classes[i]=fields[i].getDeclaringClass();
            if((alias=fields[i].getAnnotation(Alias.class))!=null){
                //别名 和字段名 都可以使用
                info.fieldMap.put(alias.value(),fields[i]);
                info.fieldMap.put(fields[i].getName(),fields[i]);
            }else if((useHump=c.getAnnotation(UseHump.class))!=null){
                info.fieldMap.put(changeHumpToLowerCase(fields[i].getName(),useHump),fields[i]);
            }else{
                info.fieldMap.put(fields[i].getName(),fields[i]);
            }
        }
        return info;
    }
    
    private static String changeHumpToLowerCase(String str,UseHump useHump){
        StringBuilder sb=new StringBuilder();
        char[] arr=str.toCharArray();
        int start='A'+(useHump.value()==UseHump.Change.HUMP_TO_LOWER?0:32);
        int end='Z'+(useHump.value()==UseHump.Change.HUMP_TO_LOWER?0:32);
        int change=useHump.value()==UseHump.Change.HUMP_TO_LOWER?32:-32;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]>=start&&arr[i]<=end){
                sb.append(useHump.separator());
                sb.append((char)(arr[i]+change));
            }else {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    private static class BeanFieldsInfo{
        private Map<String, Field> fieldMap;
        private Class<?>[] classes;
    }

}
