package cn.gjy.blog.controller.user;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.model.CheckResult;
import cn.gjy.blog.model.MenuModel;
import cn.gjy.blog.model.SysUser;
import cn.gjy.blog.service.CommonService;
import cn.gjy.blog.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
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

    @InitObject
    private CommonService commonService;

    @Route("/")
    public String index(@BindParam(value = ContentString.USER_SESSION_TAG
            ,from = HttpSession.class) SysUser sysUser){
        if(sysUser!=null)
            return dashboard();
        return "user/login";
    }

    /**
     *
     * @param sysUser
     * @param session
     * @param vcode
     * @param model
     * @param referTo 登录后重定向的地址
     * @return
     */
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
        return "redirect:"+FrameworkConfig.contentPath+ "/user/dashboard";
    }

    @Route(value = "/dashboard",method = {Route.HttpMethod.GET})
    public String dashboard(){
        return "user/dashboard";
    }

    //后台首页
    @Route(value = "/welcome",method = Route.HttpMethod.GET)
    public String welcome(Model model,@BindParam(value = ContentString.USER_SESSION_TAG,
            from = HttpSession.class) SysUser user){
        model.setAttribute("nowTime",commonService.getHourWelcome(null));
        model.setAttribute("operations",commonService.getUserOperations(user,null));
        model.setAttribute("blogCount",userService.getUserBlogCount(user));
        model.setAttribute("recentBlogs",userService.getUserRecentBlogs(user));
        return "user/welcome";
    }

    @ResponseBody
    @Route("/menu")
    public MenuModel getUserMenu(){
        return userService.getUserMenuData();
    }

    @Route("/follow")
    public String follow(@BindParam(value = ContentString.USER_SESSION_TAG
            ,from = HttpSession.class) SysUser sysUser
            ,Model model){
        model.setAttribute(ContentString.CHILD_JSP,FrameworkConfig.getJspPath("user/follow"));
        return ContentString.BASE_JSP;
    }

    //退出登录
    @Route(value = "/exit")
    public String exitLogin(HttpSession session){
        session.invalidate();
        return "redirect:"+FrameworkConfig.contentPath+ "/user/";
    }

}
