package cn.gjy.blog.controller.user;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.model.CheckResult;
import cn.gjy.blog.model.MenuModel;
import cn.gjy.blog.model.SysUser;
import cn.gjy.blog.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class UserController
 */
@Route("/user")
@Controller
public class UserController {

    @InitObject
    private UserService userService;

    @Route("/")
    public String index(@BindParam(value = ContentString.USER_SESSION_TAG
            ,from = HttpSession.class) SysUser sysUser){
        if(sysUser!=null)
            return dashboard();
        return "user/login";
    }

    @SuppressWarnings("all")
    @Route(value = "/login",method = Route.HttpMethod.POST)
    public String login(@BindParam SysUser sysUser, HttpSession session
            , @BindParam("vcode") String vcode, Model model){
        //登录失败自动填写账号密码
        model.setAttribute("username",sysUser.getUsername());
        model.setAttribute("password",sysUser.getPassword());
        if(session.getAttribute(ContentString.V_CODE)==null||
                !((String)session.getAttribute(ContentString.V_CODE)).equalsIgnoreCase(vcode)){
            model.setAttribute("msg","*验证码错误!");
            return "user/login";
        }
        CheckResult<SysUser> loginResult=userService.loginUser(sysUser);
        if(loginResult.getResult()== CheckResult.State.FAIL){
            model.setAttribute("msg",loginResult.getMsg());
            return "/user/login";
        }
        //登录成功
        session.setAttribute(ContentString.USER_SESSION_TAG,loginResult.getData());
        return dashboard();
    }

    @Route(value = "/dashboard",method = {Route.HttpMethod.GET})
    public String dashboard(){
        return "user/dashboard";
    }

    //后台首页
    @Route(value = "/welcome",method = Route.HttpMethod.GET)
    public String welcome(Model model){

        return "user/welcome";
    }

    @ResponseBody
    @Route("/menu")
    public MenuModel getUserMenu(){
        return userService.getUserMenuData();
    }

    @Route("/follow")
    public String follow(@BindParam(value = ContentString.USER_SESSION_TAG
            ,from = HttpSession.class) SysUser sysUser){
        return "user/follow";
    }

    //退出登录
    @Route(value = "/exit")
    public String exitLogin(HttpSession session){
        session.invalidate();
        return "user/login";
    }

}