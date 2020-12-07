package cn.gjy.blog.framework.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface CharsetConfig {

    void handle(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException;

}
