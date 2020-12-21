package cn.gjy.blog.interceptor;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.dao.UserDao;
import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.http.Interceptor;
import cn.gjy.blog.framework.log.SimpleLog;
import cn.gjy.blog.model.SysUser;
import cn.gjy.blog.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class UserInterceptor
 * 拦截未登录用户
 */
@Config(Interceptor.class)
public class UserInterceptor implements Interceptor {

    private static final SimpleLog log=SimpleLog.log(UserInterceptor.class);

    @InitObject
    private UserService userService;

    @Override
    public String registerPatten() {
//        return "/user/.+?";
        return "/user/manage/.+?";
    }

    @Override
    public String registerExcludePatten() {
        return "/user/manage/login";
//        return "";
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Method method, Object methodObject) throws Exception {
        if(request.getSession().getAttribute(ContentString.USER_SESSION_TAG) != null)
            return true;
//        //测试用
//        //添加重定向地址到session
//        SysUser sysUser=userService.getTestUser();
//        System.out.println(sysUser);
//        request.getSession().setAttribute(ContentString.USER_SESSION_TAG,sysUser);
//        return true;
        response.sendRedirect(request.getContextPath()+"/user/manage/");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Method method, Object[] methodObject, Object returnData) throws Exception {

    }

}
