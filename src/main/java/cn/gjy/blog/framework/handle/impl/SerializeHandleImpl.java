package cn.gjy.blog.framework.handle.impl;
import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.handle.SerializeHandle;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author gujianyang
 * @date 2020/11/29
 */
@Config(SerializeHandle.class)
public class SerializeHandleImpl implements SerializeHandle {

    private static final Gson gson=new Gson();

    @Override
    public List<Class<?>> register() {
        return null;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Method method, Object returnData) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(gson.toJson(returnData));
        response.getWriter().flush();
    }

}
