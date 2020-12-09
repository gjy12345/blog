package cn.gjy.blog.common;

import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.handle.DataHandle;
import cn.gjy.blog.framework.handle.impl.StringDataHandle;
import cn.gjy.blog.model.BlogConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class SelfStringDataHandle
 * 覆盖原StringDataHandle
 */
@Config(value = DataHandle.class,level = 99)
public class SelfStringDataHandle extends StringDataHandle {

    @InitObject
    private BlogConfig blogConfig;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Method method, Object returnData) throws IOException, ServletException {
        String data= (String) returnData;
        if(data.contains("redirect:")){
            //重定向
            response.sendRedirect(data.replace("redirect:",""));
            return;
        }
        request.setAttribute("blogConfig", blogConfig);
        super.handle(request, response, method, returnData);
    }

}
