package cn.gjy.blog.controller;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.Controller;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.utils.VcodeUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author gujianyang
 * @Date 2020/12/8
 * @Class CommonController
 * 通用方法
 */
@Route("/common")
@Controller
public class CommonController {


    /**
     * 获取验证码
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @Route("/vcode")
    public void getVerificationCode(HttpSession session, HttpServletResponse response) throws Exception {
        VcodeUtils vcodeUtils=new VcodeUtils();
        BufferedImage image = vcodeUtils.getImage();
        session.setAttribute(ContentString.V_CODE,vcodeUtils.getText());
        VcodeUtils.writeImage(image,response.getOutputStream());
    }
}