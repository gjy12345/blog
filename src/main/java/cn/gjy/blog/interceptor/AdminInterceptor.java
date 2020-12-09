package cn.gjy.blog.interceptor;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.http.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class AdminInterceptor
 */
@Config(Interceptor.class)
public class AdminInterceptor implements Interceptor {
    @Override
    public String registerPatten() {
        return "/admin/.+?";
    }

    @Override
    public String registerExcludePatten() {
        return "/admin/login";
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Method method, Object methodObject) throws Exception {
        if(request.getSession().getAttribute(ContentString.USER_SESSION_TAG) != null){
            return true;
        }
        response.sendRedirect("redirect:"+request.getContextPath()+"/admin/");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Method method, Object methodObject) throws Exception {

    }
}
