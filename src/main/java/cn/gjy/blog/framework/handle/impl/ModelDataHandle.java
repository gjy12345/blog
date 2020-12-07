package cn.gjy.blog.framework.handle.impl;

import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.handle.DataHandle;
import cn.gjy.blog.framework.model.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gujianyang
 * @date 2020/11/30
 */
@Config(DataHandle.class)
public class ModelDataHandle implements DataHandle {
    @Override
    public List<Class<?>> register() {
        List<Class<?>> list=new ArrayList<>();
        list.add(Model.class);
        list.add(ModelAndView.class);
        return list;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Method method, Object returnData) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        if(method.getReturnType()==ModelAndView.class){
            request.getRequestDispatcher(((ModelAndView) returnData).getView()).forward(request,response);
        }
    }
}
