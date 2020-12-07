package cn.gjy.blog.framework.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gujianyang
 * @date 2020/11/29
 */
public class Model {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public Model(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void setAttribute(String key, Object value){
        request.setAttribute(key,value);
    }

}
