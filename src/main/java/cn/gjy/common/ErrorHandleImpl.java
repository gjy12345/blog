package cn.gjy.common;

import cn.gjy.framerwork.annotation.Config;
import cn.gjy.framerwork.annotation.Route;
import cn.gjy.framerwork.handle.ErrorHandle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
@Config(ErrorHandle.class)
public class ErrorHandleImpl implements ErrorHandle {

    @Override
    public void onException(Exception e, String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response,
                            Method method) throws Exception{
        response.getWriter().write("fail");
    }

    @Override
    public void onUrlNotMatch(String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.getWriter().write("404 not found");
    }
}
