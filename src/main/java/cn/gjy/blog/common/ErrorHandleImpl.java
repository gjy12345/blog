package cn.gjy.blog.common;

import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.handle.ErrorHandle;
import cn.gjy.blog.model.BlogConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
@Config(value = ErrorHandle.class,level = 99)
public class ErrorHandleImpl implements ErrorHandle {

    @InitObject
    private BlogConfig blogConfig;

    @Override
    public void onException(Exception e, String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response,
                            Method method) throws Exception{
        request.setAttribute(ContentString.CHILD_JSP,FrameworkConfig.getJspPath("500"));
        request.setAttribute("blogConfig", blogConfig);
        request.getRequestDispatcher(FrameworkConfig.getJspPath("base")).forward(request,response);
    }

    @Override
    public void onUrlNotMatch(String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.setAttribute(ContentString.CHILD_JSP,FrameworkConfig.getJspPath("404"));
        request.setAttribute("blogConfig", blogConfig);
        request.getRequestDispatcher(FrameworkConfig.getJspPath("base")).forward(request,response);
    }

    @Override
    public void onNoSuchMethod(String url, HttpServletRequest request, HttpServletResponse response, String httpMethod) {

    }
}
