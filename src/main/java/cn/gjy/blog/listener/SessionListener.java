package cn.gjy.blog.listener;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.log.SimpleLog;
import cn.gjy.blog.model.SysUser;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author gujianyang
 * @Date 2020/12/10
 * @Class SessionListener
 */
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    private static Map<Integer, HttpSession> userSessionMap=new ConcurrentHashMap<>();

    private static SimpleLog log=SimpleLog.log(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //失效调用
        Object o=httpSessionEvent.getSession().getAttribute(ContentString.USER_SESSION_TAG);
        if(o==null){
            o=httpSessionEvent.getSession().getAttribute(ContentString.ADMIN_SESSION_TAG);
        }
        if(o!=null){
            SysUser sysUser= (SysUser) o;
            log.v("失效用户登录状态:"+sysUser.getId()+" "+sysUser.getUsername());
            log.v("当前用户数量:"+userSessionMap.size());
            userSessionMap.remove(sysUser.getId());
        }
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        if (se.getName().equals(ContentString.USER_SESSION_TAG)||
                se.getName().equals(ContentString.ADMIN_SESSION_TAG)) {
            SysUser user= (SysUser) se.getValue();
            if(userSessionMap.containsKey(user.getId())){
                log.v("重复登录 退出上一个登录状态:"+user.getId());
                //退出用户
                userSessionMap.get(user.getId()).removeAttribute(se.getName());
            }
            log.v("用户登录:"+user.getId());
            //替换用户session
            userSessionMap.put(user.getId(),se.getSession());
            log.v("当前用户数量:"+userSessionMap.size());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {

    }

    public static void exitUser(Integer userId){
        if(userSessionMap.containsKey(userId)){
            log.v("移除用户登录状态:"+userId);
            userSessionMap.get(userId).invalidate();
        }
    }
}
