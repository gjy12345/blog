package cn.gjy.framerwork.factory.impl;

import cn.gjy.framerwork.Invocation.DaoInvocationHandlerImpl;
import cn.gjy.framerwork.Invocation.ServiceInvocationHandlerImpl;
import cn.gjy.framerwork.annotation.*;
import cn.gjy.framerwork.log.SimpleLog;
import cn.gjy.framerwork.tool.ClassTool;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author gujianyang
 * @date 2020/11/27
 */
public class ObjectFactory {

    private List<Class<?>> classes;
    private Map<Class<?>,Object> objectMap;
    private List<Object> controllerList;
    private static final SimpleLog log=SimpleLog.log(ObjectFactory.class);

    public ObjectFactory() throws Exception {
        this.classes= ClassTool.loadProjectAllClasses();
    }

    public void setControllerList(List<Object> controllerList) {
        this.controllerList = controllerList;
    }

    public ObjectFactory(List<Class<?>> classes){
        this.classes=classes;
    }

    public Map<Class<?>, Object> getObjectMap() {
        return objectMap;
    }

    public void init() throws IllegalAccessException {
        objectMap=new HashMap<>();
        objectMap.put(this.getClass(),this);
        List<Class<?>> services=new ArrayList<>();
        List<Class<?>> dao=new ArrayList<>();
        classes.parallelStream().filter(aClass -> aClass.getAnnotation(Service.class)!=null||
                aClass.getAnnotation(Dao.class)!=null).forEach(aClass -> {
                    if(aClass.getAnnotation(Service.class)!=null){
                        services.add(aClass);
                    }else {
                        dao.add(aClass);
                    }
        });
        log.v("services:"+services.size());
        log.v("dao:"+dao.size());
        initServices(services);
        initDaos(dao);
        if (this.controllerList!=null) {
            for (int i = 0; i < this.controllerList.size(); i++) {
                this.objectMap.put(this.controllerList.get(i).getClass(),this.controllerList.get(i));
            }
        }
        Iterator<Class<?>> iterator = this.objectMap.keySet().iterator();
        Class<?> c,find;
        Field[] fields;
        Object o,findO;
        FindByClass findByClass;
        while (iterator.hasNext()) {
            c=iterator.next();
            fields=c.getDeclaredFields();
            o=objectMap.get(c);
            for (Field field : fields) {
                if(field.getAnnotation(InitObject.class)==null){
                    continue;
                }
                if((findByClass=field.getAnnotation(FindByClass.class))!=null){
                    find=findByClass.value();
                }else {
                    find=field.getType();
                }
                if((findO=objectMap.get(find))!=null){
                    field.setAccessible(true);
                    field.set(o,findO);
                    log.v("装配:"+o.getClass().getName()+" 字段:"+field.getName()+" 成功");
                }else {
                    log.e("装配:"+o.getClass().getName()+" 字段:"+field.getName()+" 失败");
                    throw new RuntimeException("没有对应的类型可以注入:"+find.getName());
                }
            }
        }
    }

    private void initDaos(List<Class<?>> dao) {
        dao.stream().forEach(aClass -> {
            Object o=new DaoInvocationHandlerImpl<>(aClass).getProxy();
            objectMap.put(aClass,o);
        });
    }

    private void initServices(List<Class<?>> serviceClasses){
        serviceClasses.forEach(aClass -> {
                    try {
                        if(aClass.getInterfaces().length==0){
                            log.e("JDK代理对象必须是一个接口的实现类!"+aClass.getName());
                            throw new RuntimeException("JDK代理对象必须是一个接口的实现类:"+aClass.getName());
                        }
                        if(aClass.isInterface()){
                            log.e("JDK代理对象必须是一个接口的实现类而不是接口:"+aClass.getName());
                            throw new RuntimeException("JDK代理对象必须是一个接口的实现类而不是接口:"+aClass.getName());
                        }
                        Service service=aClass.getAnnotation(Service.class);
                        Object o=aClass.newInstance();
                        Object proxyInstance = new ServiceInvocationHandlerImpl(o).getProxy();
                        objectMap.put(service.value(),proxyInstance);
                        objectMap.put(aClass,proxyInstance);
                        log.v("动态代理: "+service.value()+" 成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public Object getObjectByClass(Class<?> aClass) throws IllegalAccessException, InstantiationException {
        if(this.objectMap==null){
            this.objectMap=new HashMap<>();
        }
        Object t;
        if((t=this.objectMap.get(aClass))==null){
            t=aClass.newInstance();
            this.objectMap.put(aClass,t);

        }
        return t;
    }
}
