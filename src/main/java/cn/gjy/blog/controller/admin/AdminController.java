package cn.gjy.blog.controller.admin;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.listener.SessionListener;
import cn.gjy.blog.model.*;
import cn.gjy.blog.service.AdminService;
import cn.gjy.blog.service.CommentService;
import cn.gjy.blog.service.CommonService;
import cn.gjy.blog.service.UserService;
import cn.gjy.blog.utils.TimeUtils;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/8
 * @Class AdminController
 */
@XssFilter
@Route("/admin")
@Controller
public class AdminController {

    @InitObject
    private AdminService adminService;

    @InitObject
    private UserService userService;

    @InitObject
    private CommentService commentService;

    @InitObject
    private CommonService commonService;

    @Route("/")
    public String index(@BindParam(value = ContentString.ADMIN_SESSION_TAG,from = HttpSession.class) SysUser sysUser){
        if(sysUser==null){
            return "admin/login";
        }
        return "redirect:"+ FrameworkConfig.contentPath+ "/admin/dashboard";
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
        CheckResult<SysUser> loginResult=userService.loginUser(sysUser,SysUser.ADMIN);
        if(loginResult.getResult()== CheckResult.State.FAIL){
            model.setAttribute("msg",loginResult.getMsg());
            return "admin/login";
        }
        //登录成功
        session.setAttribute(ContentString.ADMIN_SESSION_TAG,loginResult.getData());
        return "admin/dashboard";
    }

    @Route(value = "/dashboard",method = {Route.HttpMethod.GET})
    public String dashboard(){
        return "admin/dashboard";
    }

    //退出登录
    @Route(value = "/exit")
    public String exitLogin(HttpSession session){
        session.invalidate();
        return "admin/index";
    }

    @ResponseBody
    @Route("/menu")
    public MenuModel getUserMenu(){
        return userService.getUserMenuData(SysUser.ADMIN);
    }

    //后台首页
    @Route(value = "/welcome",method = Route.HttpMethod.GET)
    public String welcome(Model model,@BindParam(value = ContentString.ADMIN_SESSION_TAG,
            from = HttpSession.class) SysUser user) {
        model.setAttribute("nowTime",commonService.getHourWelcome(null));
        model.setAttribute("userCount",adminService.getUserCount());
        model.setAttribute("blogCount",adminService.getBlogCount());
        model.setAttribute("commentCount",adminService.getCommentCount());
        model.setAttribute("online", SessionListener.getUserSessionMap().size());
        return "admin/welcome";
    }

    @Route(value = "/user/list",method = Route.HttpMethod.GET)
    public String userList(){
        return "admin/user_list";
    }

    @ResponseBody
    @Route(value = "/user/list",method = Route.HttpMethod.POST)
    public TableData<List<DetailedSysUser>> getUserList(@BindParam SysUser sysUser,
                                                        @BindParam("page") Integer page,
                                                        @BindParam("keyword") String keyword){
        return adminService.getUserTableData(sysUser,page,keyword);
    }

    @Route("/user/add")
    public String toAddUser(){
        return "admin/add_user";
    }

    @ResponseBody
    @Route(value = "/user/add",method = Route.HttpMethod.POST)
    public CheckResult<Void> addUser(@RequestBody SysUser sysUser){
        return adminService.addNewUser(sysUser);
    }

    @Route("/user/edit")
    public String toEditUser(Model model,@BindParam("userId") Integer userId){
        model.setAttribute("user",adminService.getUserById(userId));
        return "admin/edit_user";
    }

    @ResponseBody
    @Route(value = "/user/edit",method = Route.HttpMethod.POST)
    public CheckResult<Void> editUser(@RequestBody SysUser uploadUser){
        return adminService.editUser(uploadUser);
    }

    @ResponseBody
    @Route(value = "/user/delete",method = Route.HttpMethod.POST)
    public CheckResult<Void> deleteUser(@BindParam("id") Integer id){
        return adminService.deleteUserById(id);
    }

    @Route("/blog/list")
    public String blogList(){
        return "admin/blog_list";
    }

    @ResponseBody
    @Route(value = "/blog/list",method = Route.HttpMethod.POST)
    public TableData<List<DetailedArticle>> getBlogData(@BindParam Article article,
                                                        @BindParam("page") Integer page) {
        return adminService.getBlogList( article, page, false);
    }

    @ResponseBody
    @Route(value = "/blog/delete",method = Route.HttpMethod.POST)
    public CheckResult<Void> deleteBlog(@BindParam("id") Integer id){
        return adminService.deleteBlogById(id);
    }

    @Route(value = "/blog/lockOrUnlock",method = Route.HttpMethod.POST)
    @ResponseBody
    public CheckResult<Void> lockOrUnlockBlog(@BindParam("changeStatus") Integer changeStatus,
                                              @BindParam("id") Integer id){
        return adminService.lockOrUnlockBlog(id,changeStatus);
    }

    @ResponseBody
    @Route(value = "/user/exitLogin",method = Route.HttpMethod.POST)
    public CheckResult<Void> exitUser(@BindParam("id") Integer id){
        return SessionListener.exitUser(id)?CheckResult.createSuccessResult(null,"退出成功"):
                CheckResult.createFailResult("退出失败");
    }


    @Route(value = "/comment/list",method = Route.HttpMethod.GET)
    public String toCommentList(){
        return "admin/comment_list";
    }

    @ResponseBody
    @Route(value = "/comment/list",method = Route.HttpMethod.POST)
    public TableData<List<Comment>> getCommentListData(@BindParam("keyword") String keyword,
                                                       @BindParam("page") Integer page){
        return adminService.getCommentListData(page,keyword);
    }

    @Route(value = "/comment/delete",method = Route.HttpMethod.POST)
    @ResponseBody
    public CheckResult<Void> deleteComment(@BindParam("id") Integer id){
        return adminService.deleteCommentById(id);
    }
}
