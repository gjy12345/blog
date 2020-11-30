package cn.gjy.common;

import cn.gjy.framerwork.annotation.Config;
import cn.gjy.framerwork.config.CharsetConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author gujianyang
 * @date 2020/11/30
 */
//@Config(CharsetConfig.class)
public class CharsetConfigImpl implements CharsetConfig {

    private static final String CHARSET="utf-8";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding(CHARSET);
        response.setCharacterEncoding(CHARSET);
    }
}
