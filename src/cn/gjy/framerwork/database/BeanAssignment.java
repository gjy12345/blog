package cn.gjy.framerwork.database;

import cn.gjy.framerwork.Model;
import cn.gjy.framerwork.annotation.Alias;
import cn.gjy.framerwork.annotation.Controller;
import cn.gjy.framerwork.annotation.UseHump;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanAssignment {

    private static final Map<String,BeanFieldsInfo> infoMap=new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T assignmentBean(ResultSet resultSet,Class<T> tClass) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(!resultSet.next()){
            return null;
        }
        if (!resultSet.isLast()) {
            System.err.println("有多个结果被丢弃，因为当前转换模式为:转换单个对象");
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

    private static  <T> T assignmentBean(ResultSet resultSet,Class<T> tClass,BeanFieldsInfo info,ResultSetMetaData metaData) throws SQLException, IllegalAccessException, InstantiationException {
        if(Map.class.isAssignableFrom(tClass)){
            return (T) valueToMap(resultSet,resultSet.getMetaData());
        }else if(tClass.isInterface()){
            throw new RuntimeException("interface can't instance  :"+tClass);
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

    private static Map<String,Object> valueToMap(ResultSet resultSet, ResultSetMetaData metaData) throws SQLException {
        Map<String,Object> map=new HashMap<>();
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
        Class<?> c=Class.forName(className);
        BeanFieldsInfo info=new BeanFieldsInfo();
        info.fieldMap=new HashMap<>();
        Field[] fields = c.getDeclaredFields();
        info.classes=new Class[fields.length];
        Alias alias;
        UseHump useHump;
        for (int i = 0,l=fields.length; i < l; i++) {
            fields[i].setAccessible(true);
            info.classes[i]=fields[i].getDeclaringClass();
            if((alias=fields[i].getAnnotation(Alias.class))!=null){
                //别名
                info.fieldMap.put(alias.value(),fields[i]);
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
        private Class[] classes;
    }

}
