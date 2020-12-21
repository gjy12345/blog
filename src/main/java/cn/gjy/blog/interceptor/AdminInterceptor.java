package cn.gjy.blog.interceptor;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.http.Interceptor;
import cn.gjy.blog.service.UserService;

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

    @InitObject
    private UserService userService;
    @Override
    public String registerPatten() {
        return "/admin/.*?";
    }

    @Override
    public String registerExcludePatten() {
        return "/admin/login";
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Method method, Object methodObject) throws Exception {
        if(request.getSession().getAttribute(ContentString.ADMIN_SESSION_TAG) != null){
            return true;
        }
//        response.sendRedirect("redirect:"+request.getContextPath()+"/admin/");
//        return false;
        request.getSession().setAttribute(ContentString.ADMIN_SESSION_TAG,userService.getTestAdminUser());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Method method, Object[] methodObject, Object returnData) throws Exception {

    }


}
