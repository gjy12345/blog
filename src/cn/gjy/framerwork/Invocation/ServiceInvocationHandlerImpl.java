package cn.gjy.framerwork.Invocation;

import cn.gjy.framerwork.annotation.Config;
import cn.gjy.framerwork.log.SimpleLog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author gujianyang
 * @date 2020/11/26
 * 使用jdk动态代理来开启提交事务
 */
public class ServiceInvocationHandlerImpl implements InvocationHandler {

    private static SimpleLog log=SimpleLog.log(ServiceInvocationHandlerImpl.class);

    private Object o;

    public ServiceInvocationHandlerImpl(Object o){
        this.o=o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue=null;
        log.v("事务开启.");
        try {
            returnValue=method.invoke(o,args);
            log.v("事务提交.");
        }catch (Exception e){
            e.printStackTrace();
            log.v("事务回滚.");
        }finally {

        }
        return returnValue;
    }

}
