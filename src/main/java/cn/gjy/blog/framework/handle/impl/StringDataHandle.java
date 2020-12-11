package cn.gjy.blog.framework.handle.impl;

import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.handle.DataHandle;
import cn.gjy.blog.framework.log.SimpleLog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * @author gujianyang
 * @date 2020/11/30
 */
@Config(DataHandle.class)
public class StringDataHandle implements DataHandle {

    private static final SimpleLog log=SimpleLog.log(StringDataHandle.class);
    @Override
    public List<Class<?>> register() {
        return Collections.singletonList(String.class);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Method method, Object returnData) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        log.d(FrameworkConfig.getJspPath((String) returnData));
        request.getRequestDispatcher(FrameworkConfig.getJspPath((String) returnData))
                .forward(request,response);
    }
}
