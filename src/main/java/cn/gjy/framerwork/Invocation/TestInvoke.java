package cn.gjy.framerwork.Invocation;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
public class TestInvoke implements Closeable {

    public static void main(String[] args) {
        TestService testService=new TestServiceImpl();
        InvocationHandler invocationHandler=new ServiceInvocationHandlerImpl(testService);
        TestService t= (TestService) Proxy.newProxyInstance(testService.getClass().getClassLoader(),
                testService.getClass().getInterfaces(),invocationHandler);
//        t.toString();
        t.setTestService(t);
        t.run();
////        t.r();
//        Class<?>[] interfaces = TestServiceImpl.class.getInterfaces();
//        for (int i = 0; i < interfaces.length; i++) {
//            System.out.println(interfaces[i]);
//        }
    }

    @Override
    public void close() throws IOException {

    }
}
