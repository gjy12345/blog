package cn.gjy.framerwork.factory;

import cn.gjy.framerwork.annotation.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author gujianyang
 * @date 2020/11/30
 */
public interface DataHandleFactory {

    void init(List<Object> handles) throws IllegalAccessException, InstantiationException;

    void handle(HttpServletRequest request, HttpServletResponse response, Method method, Object returnData) throws IOException, ServletException;

}
