package cn.gjy.blog.framework.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author gujianyang
 * @Date 2020/12/13
 * @Class HttpRequestUtil
 */
public class HttpRequestUtil {
    private static ThreadLocal<HttpServletRequest> requestThreadLocal=new ThreadLocal<>();

    public static void setRequest(HttpServletRequest request){
        requestThreadLocal.set(request);
    }

    public static void removeRequest(){
        requestThreadLocal.remove();
    }

    public static HttpServletRequest getRequest(){
        return requestThreadLocal.get();
    }
}
