package cn.gjy.framerwork.handle;

import cn.gjy.framerwork.annotation.Route;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

//异常处理
public interface ErrorHandle {

    void onException(Exception e, String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response,Method method) throws Exception;

    void onUrlNotMatch(String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
