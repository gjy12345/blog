package cn.gjy.blog.controller.user;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.Controller;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.model.SysUser;

import javax.servlet.http.HttpSession;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class UserController
 */
@Route("/user")
@Controller
public class UserController {

    @Route("/")
    public String index(@BindParam(value = ContentString.USER_SESSION_ID
            ,from = HttpSession.class) SysUser sysUser){
        return "user/login";
    }


}
