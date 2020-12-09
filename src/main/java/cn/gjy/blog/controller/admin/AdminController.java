package cn.gjy.blog.controller.admin;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.Controller;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.model.SysUser;

import javax.servlet.http.HttpSession;

/**
 * @Author gujianyang
 * @Date 2020/12/8
 * @Class AdminController
 */
@Route("/admin")
@Controller
public class AdminController {

    @Route("/")
    public String index(@BindParam(value = ContentString.ADMIN_SESSION_TAG,from = HttpSession.class) SysUser sysUser){
        if(sysUser!=null){
            return "admin/dash";
        }
        return "admin/login";
    }

    @SuppressWarnings("all")
    @Route(value = "/login",method = Route.HttpMethod.POST)
    public String login(@BindParam SysUser sysUser, HttpSession session
                    , @BindParam("vcode") String vcode, Model model){
        if(session.getAttribute(ContentString.V_CODE)==null||
                !((String)session.getAttribute(ContentString.V_CODE)).equalsIgnoreCase(vcode)){
            model.setAttribute("msg","*验证码错误!");
            model.setAttribute("username",sysUser.getUsername());
            model.setAttribute("password",sysUser.getPassword());
            return "admin/login";
        }
        return "admin/dash";
    }

    //退出登录
    @Route(value = "/exit")
    public String exitLogin(HttpSession session){
        session.invalidate();
        return "admin/index";
    }
}
