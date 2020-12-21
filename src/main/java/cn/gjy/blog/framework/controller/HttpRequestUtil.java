package cn.gjy.blog.framework.controller;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.model.SysUser;

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

    public static String getRemoteIp(){
       return getRequest().getRemoteAddr();
    }

    public static Integer getUserId(){
        if(requestThreadLocal.get()!=null){
            SysUser attribute = (SysUser) requestThreadLocal.get().getSession().getAttribute(ContentString.USER_SESSION_TAG);
            if(attribute!=null)
                return attribute.getId();
        }
        return null;
    }

    public static boolean isAdmin(){
        if(requestThreadLocal.get()!=null){
            return requestThreadLocal.get().getSession().getAttribute(ContentString.ADMIN_SESSION_TAG)!=null;
        }
        return false;
    }

    public static Integer getAdminUserId() {
        if(requestThreadLocal.get()!=null){
            SysUser attribute = (SysUser) requestThreadLocal.get().getSession().getAttribute(ContentString.ADMIN_SESSION_TAG);
            if(attribute!=null)
                return attribute.getId();
        }
        return null;
    }
}
