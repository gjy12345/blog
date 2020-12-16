package cn.gjy.blog.interceptor;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.http.Interceptor;
import cn.gjy.blog.model.CheckResult;
import cn.gjy.blog.model.SysUser;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author gujianyang
 * @Date 2020/12/15
 * @Class CommentInterceptor
 */
@Config(Interceptor.class)
public class CommentInterceptor implements Interceptor{

    private static Map<Integer,Long> userVisitMap=new ConcurrentHashMap<>();

    @Override
    public String registerPatten() {
        return "/article/common/action/.+?";
    }

    @Override
    public String registerExcludePatten() {
        return "/article/common/list";
    }

    private static final Gson gson=new Gson();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Method method, Object methodObject) throws Exception {
        SysUser sysUser= (SysUser) request.getSession().getAttribute(ContentString.USER_SESSION_TAG);
        if(sysUser==null){
            sysUser= (SysUser) request.getSession().getAttribute(ContentString.ADMIN_SESSION_TAG);
        }
        if(sysUser==null){
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(gson.toJson(CheckResult.createFailResult("请先登录再评论")));
            response.getWriter().flush();
            return false;
        }
        Long lastVisit;
        long now=System.currentTimeMillis();
        if((lastVisit=userVisitMap.get(sysUser.getId()))!=null&&
            now-lastVisit<3000){
            userVisitMap.put(sysUser.getId(),now);
            response.setStatus(403);
            response.getWriter().write("操作频繁");
            return false;
        }
        userVisitMap.put(sysUser.getId(),now);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Method method, Object[] methodObject, Object returnData) throws Exception {

    }

}
