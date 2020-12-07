package cn.gjy.blog.framework.Invocation;

import cn.gjy.blog.framework.annotation.Transactional;
import cn.gjy.blog.framework.database.ConnectionHolder;
import cn.gjy.blog.framework.database.ConnectionUtil;
import cn.gjy.blog.framework.log.SimpleLog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gujianyang
 * @date 2020/11/26
 * 使用jdk动态代理来开启提交事务
 */
public class ServiceInvocationHandlerImpl implements InvocationHandler {

    private static SimpleLog log=SimpleLog.log(ServiceInvocationHandlerImpl.class);
    private Map<String,Boolean> isNeedTransactional;

    private Object o;

    public ServiceInvocationHandlerImpl(Object o){
        this.o=o;
        init();
    }

    private void init() {
        this.isNeedTransactional=new HashMap<>();
        Method[] methods = this.o.getClass().getMethods();
        if(o.getClass().getAnnotation(Transactional.class)!=null){
            for (int i = 0; i < methods.length; i++) {
                this.isNeedTransactional.put(methods[i].getName(),true);
            }
        }else {
            for (int i = 0; i < methods.length; i++) {
                this.isNeedTransactional.put(methods[i].getName(),methods[i].getAnnotation(Transactional.class)!=null);
            }
        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this,args);
        }
        Object returnValue;
        boolean openTransactional=isNeedTransactional.get(method.getName());
        Connection connection=ConnectionHolder.getConnection();
        boolean nowHasConnect=true;//如果已经有数据库连接就复用连接确保事务
        if(connection==null){
            nowHasConnect=false;
            connection=ConnectionUtil.getConnection();
            ConnectionHolder.setConnection(connection);
        }
        try {
            if(openTransactional&&!nowHasConnect){
                log.v("事务开启.");
                connection.setAutoCommit(false);
            }else {
                connection.setAutoCommit(true);
            }
            returnValue=method.invoke(o,args);
            if(openTransactional&&!nowHasConnect){
                log.v("事务提交.");
                connection.commit();
                ConnectionUtil.releaseConnect(connection);
                ConnectionHolder.setConnection(null);
            }
        }catch (Exception e){
            if(openTransactional&&!nowHasConnect){
                e.printStackTrace();
                connection.rollback();
                log.v("事务回滚.");
            }
            throw e;
        }finally {
            if(!nowHasConnect){

            }
        }
        return returnValue;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(o.getClass().getClassLoader(),
                o.getClass().getInterfaces(), this);
    }

}
