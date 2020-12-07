package cn.gjy.blog.framework.handle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

//控制层返回值处理
public interface DataHandle {

    //注册对应返回值处理
    List<Class<?>> register();

    //处理
    void handle(HttpServletRequest request, HttpServletResponse response, Method method,Object returnData) throws IOException, ServletException;
}
