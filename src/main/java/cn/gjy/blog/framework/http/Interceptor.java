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

    //不被拦截的方法
    String registerExcludePatten();

    /**
     *
     * @param request
     * @param response
     * @param method 控制层方法
     * @param methodObject 入参
     * @return
     * @throws Exception
     */
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Method method, Object methodObject)
            throws Exception;

    /**
     *
     * @param request
     * @param response
     * @param method 控制层方法
     * @param methodObject 入参
     * @param returnData 控制层返回结果
     * @throws Exception
     */
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Method method, Object[] methodObject,
                         Object returnData)
            throws Exception;
}
