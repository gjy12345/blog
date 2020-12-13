package cn.gjy.blog.framework.Invocation;

import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.database.BeanAssignment;
import cn.gjy.blog.framework.database.ConnectionHolder;
import cn.gjy.blog.framework.database.ConnectionUtil;
import cn.gjy.blog.framework.database.CurdTool;
import cn.gjy.blog.framework.factory.impl.ObjectFactory;
import cn.gjy.blog.framework.log.SimpleLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gujianyang
 * @date 2020/11/27
 */
public class DaoInvocationHandlerImpl<T> implements InvocationHandler {
    private static final SimpleLog log=SimpleLog.log(DaoInvocationHandlerImpl.class);

    private final Class<T> proxyInterface;

    private Map<String,DaoBindValueInfo> bindValueInfoMap;

    private final Map<String,String> sqlMap=new HashMap<>();

    private final Map<String,Class<?>> classCache=new HashMap<>();

    private final Map<String,SqlType> methodSqlType=new HashMap<>();
    
    private final Map<String,CusMethodSql> cusMethodSqlMap=new HashMap<>();


    public DaoInvocationHandlerImpl(Class<T> proxyInterface) throws IllegalAccessException, InstantiationException {
        this.proxyInterface=proxyInterface;
        init();
    }

    private void init() throws InstantiationException, IllegalAccessException {
        Set<String> methodNameSet=new HashSet<>();
        this.bindValueInfoMap=new HashMap<>();
        Method[] methods = proxyInterface.getMethods();
        for (int i = 0; i < methods.length; i++) {
            if(methodNameSet.contains(methods[i].getName())){
                log.e(proxyInterface.getName()+" 方法名重复:"+methods[i].getName());
                throw new RuntimeException("dao 的方法名不能重复");
            }
            methodNameSet.add(proxyInterface.getName());
            if(methods[i].getAnnotation(Insert.class)!=null||methods[i].getAnnotation(Update.class)!=null
            ||methods[i].getAnnotation(Delete.class)!=null||methods[i].getAnnotation(Select.class)!=null
            ||methods[i].getAnnotation(UseCustomMethod.class)!=null){
                this.bindValueInfoMap.put(methods[i].getName(),createBindInfo(methods[i]));
            }else{
                log.e(proxyInterface.getName()+" 方法名没有sql:"+methods[i].getName());
                throw new RuntimeException("数据层接口方法必须有select,update,delete或insert注解");
            }
        }
    }

    private static final Pattern pattern=Pattern.compile("#\\{(.+?)\\}");

    private DaoBindValueInfo createBindInfo(Method method) throws IllegalAccessException, InstantiationException {
        if(method.getAnnotation(UseCustomMethod.class)!=null){
            UseCustomMethod useCustomMethod=method.getAnnotation(UseCustomMethod.class);
            CusMethodSql cusMethodSql = useCustomMethod.value().newInstance();
            cusMethodSqlMap.put(method.getName(),cusMethodSql);
            methodSqlType.put(method.getName(),useCustomMethod.sqlType());
            return null;
        }
        Annotation[] annotations={method.getAnnotation(Insert.class),
                method.getAnnotation(Update.class),
                method.getAnnotation(Select.class),
                method.getAnnotation(Delete.class)};
        String sql=null;
        int count=0;
        for (Annotation annotation : annotations) {
            if (annotation!=null) {
                count++;
            }
        }
        if(count>1){
            throw new RuntimeException("select,update,delete或insert注解不能共存");
        }
        if(annotations[0]!=null){
            sql=((Insert)annotations[0]).value();
            methodSqlType.put(method.getName(),SqlType.INSERT);
        }else if(annotations[1]!=null){
            sql=((Update)annotations[1]).value();
            methodSqlType.put(method.getName(),SqlType.UPDATE);
        }else if(annotations[2]!=null){
            sql=((Select)annotations[2]).value();
            methodSqlType.put(method.getName(),SqlType.SELECT);
        }else if(annotations[3]!=null){
            sql=((Delete)annotations[3]).value();
            methodSqlType.put(method.getName(),SqlType.DELETE);
        }
        if (sql==null) {
            throw new RuntimeException("sql 不能为空! at:"+method.getName());
        }
        Matcher matcher=pattern.matcher(sql);
        String key;
        Set<String> params=new HashSet<>();
        List<String> keys=new ArrayList<>();
        while (matcher.find()) {
            key=matcher.group(1).trim();
            params.add(key);
            keys.add(key);
        }
//        Class<?>[] parameterTypes = method.getParameterTypes();
        Parameter[] parameters = method.getParameters();
        DaoBindValueInfo bindValueInfo=new DaoBindValueInfo();
        bindValueInfo.isObject=new HashMap<>();
        bindValueInfo.fieldMap=new HashMap<>();
        bindValueInfo.objectIndex=new HashMap<>();
        bindValueInfo.keys=keys;
        Set<String> argsParam=new HashSet<>();
        Alias alias;
        String fieldName;
        for (int i = 0; i < parameters.length; i++) {
            if(getBasicDataClassSet().contains(parameters[i].getType())){
                BindParam bindParam=parameters[i].getAnnotation(BindParam.class);
                if(bindParam==null)
                    throw new RuntimeException("基础数据需要有@BindParam注解来绑定");
                else if(!params.contains(bindParam.value()))
                    throw new RuntimeException("sql中无此参数!");
                bindValueInfo.isObject.put(bindParam.value(),true);
                bindValueInfo.objectIndex.put(bindParam.value(),i);
                if(argsParam.contains(bindParam.value()))
                    throw new RuntimeException("参数重复");
                argsParam.add(bindParam.value());
            }else {
                Field[] fields = parameters[i].getType().getDeclaredFields();
                for (Field field : fields) {
                    if((alias=field.getAnnotation(Alias.class))!=null){
                        fieldName=alias.value();
                    }else {
                        fieldName=field.getName();
                    }
                    if(params.contains(fieldName)){
                        bindValueInfo.isObject.put(fieldName,false);
                        bindValueInfo.objectIndex.put(fieldName,i);
                        bindValueInfo.fieldMap.put(fieldName,field);
                        if(argsParam.contains(fieldName))
                            throw new RuntimeException("参数重复");
                        argsParam.add(fieldName);
                    }
                }
            }
        }
        //System.out.println(sql.replaceAll("#\\{(.+?)\\}","?"));
        this.sqlMap.put(method.getName(),sql.replaceAll("#\\{(.+?)\\}","?"));
        String param;
        for (int i = 0; i < bindValueInfo.keys.size(); i++) {
            param=bindValueInfo.keys.get(i);
            try {
                if(bindValueInfo.isObject.get(param)){
                    log.d(param + "  是对象 args下标" + bindValueInfo.objectIndex.get(param));
                }else {
                    log.d(param + "  是字段 args下标" + bindValueInfo.objectIndex.get(param)+" field:"
                            +bindValueInfo.fieldMap.get(param));
                }
            }catch (Exception e){
                throw new RuntimeException(param+" 没有找到匹配的字段");
            }
        }
        if(params.size()==argsParam.size())
            return bindValueInfo;
        else
            throw new RuntimeException("参数不匹配");
    }

    public static Set<Class<?>> getBasicDataClassSet() {
        return BeanAssignment.getBasicDataClassSet();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this,args);
        }
        Object returnValue=null;
        boolean needReleaseConnection=false;
        try {
            if(ConnectionHolder.getConnection()==null){
                needReleaseConnection=true;
                ConnectionHolder.setConnection(ConnectionUtil.getConnection());
            }
            SqlType sqlType;
            DaoBindValueInfo info;
            StringBuilder sb=new StringBuilder();
            Object[] sqlArgs;
            String sql;
            if((info=bindValueInfoMap.get(method.getName()))!=null){
                sql=sqlMap.get(method.getName());
                log.v("SQL           ===>"+  sql);
                String param;
                sb.append("Param         ===> ");
                sqlArgs=new Object[info.keys.size()];
                for (int i = 0,l=info.keys.size(); i < l; i++) {
                    param=info.keys.get(i);
                    int index = info.objectIndex.get(param);
                    if(i!=0){
                        sb.append(" , ");
                    }
                    if(info.isObject.get(param)){
                        sqlArgs[i]=args[index];
                    }else {
                        Field field = info.fieldMap.get(param);
                        field.setAccessible(true);
                        Object o = field.get(args[index]);
                        sqlArgs[i]=o;
                    }
                    sb.append(sqlArgs[i]);
                }
            }else {
                CusMethodSql cusMethodSql = cusMethodSqlMap.get(method.getName());
                CusMethodSql.SqlAndArgs handle = cusMethodSql.handle(args);
                sqlArgs=handle.getArgs();
                sql=handle.getSql();
                log.v("SQL           ===>"+  sql);
                sb.append("Param         ===> ");
                for (int i = 0; i < handle.getArgs().length; i++) {
                    if(i!=0){
                        sb.append(" , ");
                    }
                    sb.append(handle.getArgs()[i]);
                }
            }
            log.v(sb.toString());
            sqlType = methodSqlType.get(method.getName());
            Class<?> returnType = method.getReturnType();
            log.v("returnType    ====>"+returnType.getName());
            int returnCount=0;
            switch (sqlType){
                case DELETE:
                case INSERT:
                case UPDATE:
                    Integer count=CurdTool.update(  sql, ConnectionHolder.getConnection(),
                            sqlArgs);
                    if(returnType==Void.class||returnType==void.class){
                        log.v("舍弃");
                    }else if(returnType==Integer.class||returnType==int.class){
                        returnValue= count;
                    }else if(returnType==Long.class||returnType==long.class){
                        returnValue= count.longValue();
                    }else
                        throw new RuntimeException("DELETE,INSERT,UPDATE只能返回int,long,void,或其包装类型 at:"
                                +proxyInterface.getName()+"."+method.getName());
                    returnCount=returnValue==null?0:1;
                    break;
                case SELECT:
                    if(Map.class.isAssignableFrom(returnType)){
                        if(returnType.isInterface()){
                            returnType=HashMap.class;
                        }
                        returnValue= CurdTool.selectOne(  sql,ConnectionHolder.getConnection()
                                ,returnType,sqlArgs);
                    }else if (returnType==List.class||returnType==ArrayList.class){
                        Type genericReturnType = method.getGenericReturnType();
                        if (genericReturnType instanceof ParameterizedType) {
                            Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
                            if(actualTypeArguments.length==1){
                                Class<?> componentType;
                                if((componentType=classCache.get(actualTypeArguments[0].getTypeName()))==null){
                                    componentType=Class.forName(actualTypeArguments[0].getTypeName());
                                    classCache.put(actualTypeArguments[0].getTypeName(),componentType);
                                }
                                log.v("returnType<T> ===>"+componentType.getName());
                                if(componentType.getName().startsWith(FrameworkConfig.basePackage)
                                        ||getBasicDataClassSet().contains(componentType)
                                        ||Map.class.isAssignableFrom(componentType)){
                                    returnValue=CurdTool.selectList(  sql,ConnectionHolder.getConnection(),
                                            componentType,sqlArgs);
                                    returnCount=((List)returnValue).size();
                                }else {
                                    throw new RuntimeException("List必须指定泛型");
                                }
                            }
                        }else {
                            throw new RuntimeException("List必须指定泛型");
                        }
                    }else if(returnType.getName().startsWith(FrameworkConfig.basePackage)
                            ||getBasicDataClassSet().contains(returnType)){
                        returnValue= CurdTool.selectOne(  sql,ConnectionHolder.getConnection()
                                ,returnType,sqlArgs);
                        returnCount=(returnValue==null?0:1);
                    }else if(returnType.isArray()){
                        returnValue=CurdTool.selectArray(  sql,ConnectionHolder.getConnection()
                                ,returnType.getComponentType(),sqlArgs);
                        returnCount=(Array.getLength(returnValue));
                    }else
                        throw new RuntimeException("不支持的select返回值");
                    break;
            }
            log.v("returnCount   ===> "+returnCount);
            return returnValue;
        }catch (Exception e){
            log.e(e.getMessage());
            throw e;
        }
        finally {
            if(needReleaseConnection){
                ConnectionUtil.releaseConnect(ConnectionHolder.getConnection());
                ConnectionHolder.removeConnection();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T getProxy(){
        return (T)Proxy.newProxyInstance(proxyInterface.getClassLoader(),new Class[]{proxyInterface},this);
    }

    private static class DaoBindValueInfo{
        private Map<String,Field> fieldMap;
        private Map<String,Integer> objectIndex;
        private Map<String,Boolean> isObject;//这个参数是否可以直接传递，即从params拿出的是一个整数来获取变量的下标
        private List<String> keys;
    }

    public enum SqlType{
        INSERT,UPDATE,DELETE,SELECT
    }
}
