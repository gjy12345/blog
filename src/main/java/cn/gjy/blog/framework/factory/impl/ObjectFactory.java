package cn.gjy.blog.framework.factory.impl;

import cn.gjy.blog.framework.Invocation.DaoInvocationHandlerImpl;
import cn.gjy.blog.framework.Invocation.ServiceInvocationHandlerImpl;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.framework.log.SimpleLog;
import cn.gjy.blog.framework.tool.ClassTool;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gujianyang
 * @date 2020/11/27
 * 初始化bean
 */
public class ObjectFactory {

    private List<Class<?>> classes;
    private Map<Class<?>,Object> objectMap;
    private List<Object> controllerList;
    private static final SimpleLog log=SimpleLog.log(ObjectFactory.class);
    private Map<Class<?>,Object> realObjectMap;//存放被代理对象真实的object
    private static ObjectFactory instance=null;

    public ObjectFactory() throws Exception {
        if(instance!=null){
            throw new RuntimeException("ObjectFactory已被创建");
        }
        instance=this;
        initMap();
        this.classes= ClassTool.loadProjectAllClasses();
    }

    public void setControllerList(List<Object> controllerList) {
        this.controllerList = controllerList;
    }

    public ObjectFactory(List<Class<?>> classes){
        this.classes=classes;
        initMap();
    }

    private void initMap(){
        objectMap=new ConcurrentHashMap<Class<?>, Object>(){
            @Override
            public Object put(Class<?> key, Object value) {
//                if(this.containsKey(key)){
//                    throw new RuntimeException("已经有一个相同的接口实现对象被放入");
//                }
                return super.put(key, value);
            }
        };
    }
    public Map<Class<?>, Object> getObjectMap() {
        return objectMap;
    }

    public void init() {
        objectMap.put(this.getClass(),this);
        realObjectMap=new HashMap<>();
        List<Class<?>> services=new ArrayList<>();
        List<Class<?>> dao=new ArrayList<>();
        List<Class<?>> componentList=new ArrayList<>();
        //ArrayList线程不安全 使用串行流
        classes.stream().filter(aClass -> aClass.getAnnotation(Service.class)!=null||
                aClass.getAnnotation(Dao.class)!=null||aClass.getAnnotation(Component.class)!=null).forEach(aClass -> {
                    if(aClass.getAnnotation(Dao.class)!=null){
                        dao.add(aClass);
                        return;
                    }
                    if(aClass.isInterface())
                        return;
                    if(aClass.getAnnotation(Service.class)!=null){
                        services.add(aClass);
                    }else {
                        componentList.add(aClass);
                    }
        });
        log.v("services:"+services.size());
        log.v("dao:"+dao.size());
        initServices(services);
        initDao(dao);
        initComponents(componentList);
        if (this.controllerList!=null) {
            for (int i = 0; i < this.controllerList.size(); i++) {
                this.objectMap.put(this.controllerList.get(i).getClass(),this.controllerList.get(i));
            }
        }
    }

    public void initObjectParams() throws IllegalAccessException {
        Iterator<Class<?>> iterator = this.objectMap.keySet().iterator();
        Class<?> c,find;
        Field[] fields;
        Object o,findO;
        FindByClass findByClass;
        while (iterator.hasNext()) {
            c=iterator.next();
            if(c.getAnnotation(Service.class)!=null){
                o=realObjectMap.get(c);
            }else {
                o=objectMap.get(c);
            }
            fields=c.getDeclaredFields();
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

    private void initComponents(List<Class<?>> componentList){
        Object o;
        try {
            for (Class<?> aClass : componentList) {
                if(objectMap.containsKey(aClass))
                    continue;
                o=aClass.newInstance();
                objectMap.put(aClass,o);
            }
        }catch (Exception e){
            log.v(e.getMessage());
            e.printStackTrace();
        }
    }

    private void initDao(List<Class<?>> dao) {
        dao.stream().forEach(aClass -> {
            Object o;
            try {
                o = new DaoInvocationHandlerImpl<>(aClass).getProxy();
            } catch (Exception e) {
                e.printStackTrace();
                log.e("创建dao失败:"+e.getMessage());
                return;
            }
            objectMap.put(aClass,aClass.cast(o));
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
                        realObjectMap.put(service.value(),o);
                        realObjectMap.put(aClass,o);
                        log.v("动态代理: "+service.value()+" 成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public Object getObjectByClass(Class<?> aClass) throws IllegalAccessException, InstantiationException {
        Object t;
        if((t=this.objectMap.get(aClass))==null){
            t=aClass.newInstance();
            this.objectMap.put(aClass,t);

        }
        return t;
    }

    public void putObject(Class<?> c, Object o) {
        this.objectMap.put(c,o);
    }
}
