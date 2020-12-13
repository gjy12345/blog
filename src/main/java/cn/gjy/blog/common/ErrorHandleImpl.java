package cn.gjy.blog.common;

import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.framework.handle.ErrorHandle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
@Config(value = ErrorHandle.class,level = 99)
public class ErrorHandleImpl implements ErrorHandle {

    @Override
    public void onException(Exception e, String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response,
                            Method method) throws Exception{
//        response.getWriter().write("fail");
        response.setStatus(500);
        e.printStackTrace(response.getWriter());
        e.printStackTrace();
    }

    @Override
    public void onUrlNotMatch(String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setStatus(404);
        response.getWriter().write("404 not found");
    }

    @Override
    public void onNoSuchMethod(String url, HttpServletRequest request, HttpServletResponse response, String httpMethod) {

    }
}
