package cn.gjy.blog.framework.handle.impl;

import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.framework.handle.ErrorHandle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author gujianyang
 * @Date 2020/12/13
 * @Class DefaultErrorHandle
 */
@Config(value = ErrorHandle.class,level = 1)
public class DefaultErrorHandle implements ErrorHandle{
    @Override
    public void onException(Exception e, String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response, Method method) throws Exception {
        e.printStackTrace(response.getWriter());
    }

    @Override
    public void onUrlNotMatch(String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write("404 not found!");
    }

    @Override
    public void onNoSuchMethod(String url, HttpServletRequest request, HttpServletResponse response, String httpMethod) throws IOException {
        response.getWriter().write("no such method:"+httpMethod+"!");
    }
}
