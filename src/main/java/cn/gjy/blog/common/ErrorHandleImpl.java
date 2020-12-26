package cn.gjy.blog.common;

import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.ResponseBody;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.handle.ErrorHandle;
import cn.gjy.blog.model.BlogConfig;
import cn.gjy.blog.model.CheckResult;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
@Config(value = ErrorHandle.class,level = 99)
public class ErrorHandleImpl implements ErrorHandle {

    @InitObject
    private BlogConfig blogConfig;

    private Gson gson=new Gson();

    @Override
    public void onException(Exception e, String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response,
                            Method method) throws Exception{
        if (method.getReturnType()==String.class) {
            request.setAttribute(ContentString.CHILD_JSP,FrameworkConfig.getJspPath("500"));
            request.setAttribute("blogConfig", blogConfig);
            request.getRequestDispatcher(FrameworkConfig.getJspPath("base")).forward(request,response);
        }else if(method.getAnnotation(ResponseBody.class)!=null){
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(gson.toJson(CheckResult.createFailResult("服务器错误")));
            response.getWriter().flush();
        }else {
            response.getWriter().write("服务器错误");
            response.getWriter().flush();
        }
        e.printStackTrace();
    }

    @Override
    public void onUrlNotMatch(String url, Route.HttpMethod m, HttpServletRequest request, HttpServletResponse response) throws Exception{
        if(request.getContentType()!=null&&request.getContentType().contains("json")){
            response.getWriter().write(gson.toJson(CheckResult.createFailResult("地址为:"+url+"不存在!")));
            response.getWriter().flush();
            return;
        }
        request.setAttribute(ContentString.CHILD_JSP,FrameworkConfig.getJspPath("404"));
        request.setAttribute("blogConfig", blogConfig);
        request.getRequestDispatcher(FrameworkConfig.getJspPath("base")).forward(request,response);
    }

    @Override
    public void onNoSuchMethod(String url, HttpServletRequest request, HttpServletResponse response, String httpMethod) {

    }
}
