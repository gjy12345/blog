package cn.gjy.framerwork.handle;

import cn.gjy.framerwork.annotation.Route;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//异常处理
public interface ErrorHandle {

    void onException(Exception e, String url, Route.HttpMethod method, HttpServletRequest request, HttpServletResponse response) throws Exception;

    void onUrlNotMatch(String url, Route.HttpMethod method,HttpServletRequest request,HttpServletResponse response) throws Exception;

}
