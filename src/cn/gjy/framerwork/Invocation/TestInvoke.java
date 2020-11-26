package cn.gjy.framerwork.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
public class TestInvoke {

    public static void main(String[] args) {
        TestService testService=new TestServiceImpl();
        InvocationHandler invocationHandler=new ServiceInvocationHandlerImpl(testService);
        TestService t= (TestService) Proxy.newProxyInstance(testService.getClass().getClassLoader(),
                testService.getClass().getInterfaces(),invocationHandler);
        t.run();
    }
}
