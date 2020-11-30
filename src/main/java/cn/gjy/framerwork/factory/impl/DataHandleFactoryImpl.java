package cn.gjy.framerwork.factory.impl;

import cn.gjy.framerwork.annotation.Config;
import cn.gjy.framerwork.factory.DataHandleFactory;
import cn.gjy.framerwork.handle.DataHandle;
import cn.gjy.framerwork.log.SimpleLog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gujianyang
 * @date 2020/11/30
 */
@Config(DataHandleFactory.class)
public class DataHandleFactoryImpl implements DataHandleFactory {

    private SimpleLog log=SimpleLog.log(DataHandleFactoryImpl.class);

    private Map<Class<?>, DataHandle> handleMap;

    public void init(List<Object> handles) throws IllegalAccessException, InstantiationException {
        handleMap=new HashMap<>();
        DataHandle dataHandle;
        for (Object handle : handles) {
            dataHandle= (DataHandle) handle;
            register(dataHandle.register(),dataHandle);
        }
    }

    private void register(List<Class<?>> register, DataHandle dataHandle) {
        if (register==null)
            throw new RuntimeException("注册的返回值规则列表不能为空! at:"+dataHandle.getClass().getName());
        for (int i = 0; i < register.size(); i++) {
            if(handleMap.containsKey(register.get(i))){
                log.e("已存在关于:"+register.get(i).getName()+" 的返回值处理器!");
                throw new RuntimeException("已存在关于:"+register.get(i).getName()+" 的返回值处理器!");
            }
            handleMap.put(register.get(i),dataHandle);
            log.v("注册返回值:"+register.get(i).getName()+" 的返回值处理器.");
        }
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, Method method, Object returnData) throws IOException, ServletException {
        Class<?> returnType = method.getReturnType();
        DataHandle dataHandle;
        if((dataHandle=handleMap.get(returnType))!=null){
            dataHandle.handle(request,response,method,returnData);
        }else {
            throw new RuntimeException("没有关于返回值:"+returnType.getName()+"的处理器!");
        }
    }
}
