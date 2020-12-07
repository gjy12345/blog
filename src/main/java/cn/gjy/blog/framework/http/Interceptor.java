package cn.gjy.blog.framework.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author gujianyang
 * @date 2020/11/30
 * 正则
 */
public interface Interceptor {

    //正则表达式
    String registerPatten();

    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Method method, Object methodObject)
            throws Exception;

    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Method method, Object methodObject)
            throws Exception;
}
