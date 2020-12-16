package cn.gjy.blog.interceptor;

import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.controller.HttpRequestUtil;
import cn.gjy.blog.framework.http.Interceptor;
import cn.gjy.blog.model.SysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author gujianyang
 * @Date 2020/12/16
 * @Class BlogInterceptor
 */
//@Config(Interceptor.class)
public class BlogInterceptor implements Interceptor {

    private static final Map<String,Long> lastVisitMap=new ConcurrentHashMap<>();



    @Override
    public String registerPatten() {
        return "/article/detail";
    }

    @Override
    public String registerExcludePatten() {
        return null;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Method method, Object methodObject) throws Exception {

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Method method, Object[] methodObject, Object returnData) throws Exception {

    }
}
