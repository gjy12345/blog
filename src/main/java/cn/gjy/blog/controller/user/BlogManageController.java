package cn.gjy.blog.controller.user;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.dao.BlogDao;
import cn.gjy.blog.dao.CommentDao;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.model.*;
import cn.gjy.blog.service.BlogService;
import cn.gjy.blog.service.CommentService;
import cn.gjy.blog.service.CommonService;
import cn.gjy.blog.service.UserService;
import cn.gjy.blog.utils.TimeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class BlogController
 */
@Route("/user/manage")
@Controller
public class BlogManageController {

    @InitObject
    private BlogService blogService;

    @InitObject
    private BlogDao blogDao;

    @InitObject
    private UserService userService;

    @InitObject
    private CommentDao commentDao;

    @InitObject
    private CommentService commentService;

    @InitObject
    private CommonService commonService;


    @Route(value = "/article/new",method = Route.HttpMethod.GET)
    public String writeNewBlog(){
        return "blog/writeArticle";
    }

    //发布博客
    @ResponseBody
    @Route(value = "/article/new",method = Route.HttpMethod.POST)
    public CheckResult<Integer> releaseNewBlog(@JsonRequestBody Article article,
                                               @BindParam(value = ContentString.USER_SESSION_TAG,
                                                       from = HttpSession.class) SysUser user
            , HttpServletRequest request){
        return blogService.releaseNewBlog(article,user,request.getRemoteAddr());
    }

    //保存草稿
    public CheckResult<Integer> saveDraftBlog(){

        return null;
    }

    //选择博客的一些基本参数
    @Route("/choseOptions")
    public String choseOptions(@BindParam("blogId") Integer blogId,Model model,
                              @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class)
                                      SysUser sysUser){
        Category category=new Category();
        category.setCreateUser(sysUser.getId());
        category.setLock(ContentString.UNLOCK);
        model.setAttribute("categories",blogDao.getUserBlogCategories(category,null,null));
        if(blogId!=null){
            Article article = blogDao.selectUserBlogById(blogId, sysUser.getId());
            if(article!=null&&article.getStatus().equals(Article.ArticleState.RELEASE))
                model.setAttribute("blog",article);
        }
        return "blog/choseOptions";
    }

    //根据页数,名称查询
    @Route("/article/type/list")
    @ResponseBody
    public TableData<List<Category>> blogTypes(@BindParam("page") Integer page,
                                               @BindParam(value = ContentString.USER_SESSION_TAG,
                            from = HttpSession.class) SysUser sysUser,
                                               @BindParam Category category){
        return blogService.getUserCategories(page,sysUser,category);
    }

    @Route("/article/type")
    public String toBlogTypes(){
        return "blog/blog_category";
    }

    @Route("/article/type/add")
    public String toAddCategory(){
        return "blog/blog_category_add";
    }

    @ResponseBody
    @Route(value = "/article/type/add",method = Route.HttpMethod.POST)
    public CheckResult<Void> addCategory(@JsonRequestBody Category category,
                                         @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class)
                                                 SysUser sysUser,HttpServletRequest request){
        return blogService.addUserCategory(sysUser,category,request.getRemoteAddr());
    }

    @ResponseBody
    @Route(value = "/article/type/delete",method = Route.HttpMethod.POST)
    public CheckResult<Void> deleteCategory(@BindParam(value = "id",required = true) Integer id
        ,@BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class) SysUser sysUser){
        return blogService.deleteCategory(id,sysUser);
    }

    @Route("/article/type/edit")
    public String toEditCategory(@BindParam("id") Integer id,Model model
    ,@BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class) SysUser sysUser){
        Category category = blogDao.selectUserCategoryById(id,sysUser.getId());
        model.setAttribute("category",category);
        return "blog/blog_category_edit";
    }

    @ResponseBody
    @Route(value = "/article/type/edit",method = {Route.HttpMethod.POST})
    public CheckResult<Void> editCategory(@JsonRequestBody Category category,
        @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class)SysUser sysUser){
        return blogService.editCategory(category,sysUser);
    }

    @ResponseBody
    @Route(value = "/article/type/lockOrUnlock",method = {Route.HttpMethod.POST})
    public CheckResult<Void> lockOrUnlock(@BindParam(value = "id",required = true) Integer id,@BindParam(value = ContentString.USER_SESSION_TAG,
    from = HttpSession.class) SysUser user,@BindParam(value = "lock",required = true)Integer lock){
        return blogService.lockOrUnlock(id,lock,user);
    }

    @Route("/article/list")
    public String toBlogList(Model model,@BindParam(value = ContentString.USER_SESSION_TAG,
        from = HttpSession.class) SysUser sysUser){
        Category category=new Category();
        category.setCreateUser(sysUser.getId());
        category.setLock(ContentString.UNLOCK);
        model.setAttribute("categories",blogDao.getUserBlogCategories(category,null,null));
        return "blog/blog_list";
    }

    @ResponseBody
    @Route(value = "/article/list",method = Route.HttpMethod.POST)
    public TableData<List<DetailedArticle>> getBlogList(@BindParam(value = ContentString.USER_SESSION_TAG,
            from = HttpSession.class) SysUser sysUser,@BindParam Article article,
                                                @BindParam("page") Integer page){
        return blogService.getBlogList(sysUser,article,page,false);
    }

    @ResponseBody
    @Route(value = "/article/delete",method = Route.HttpMethod.POST)
    public CheckResult<Void> deleteArticle(@BindParam("id") Integer id,
        @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class) SysUser sysUser){
        return blogService.deleteBlog(id,sysUser);
    }

    @Route("/article/delete/list")
    public String toDeleteBlogList(Model model,@BindParam(value = ContentString.USER_SESSION_TAG,
            from = HttpSession.class) SysUser sysUser){
        Category category=new Category();
        category.setCreateUser(sysUser.getId());
        category.setLock(ContentString.UNLOCK);
        model.setAttribute("categories",blogDao.getUserBlogCategories(category,null,null));
        return "blog/blog_delete_list";
    }

    @ResponseBody
    @Route(value = "/article/delete/list",method = Route.HttpMethod.POST)
    public TableData<List<DetailedArticle>> getDeleteBlogList(@BindParam(value = ContentString.USER_SESSION_TAG,
            from = HttpSession.class) SysUser sysUser,@BindParam Article article,
                                                        @BindParam("page") Integer page){
        return blogService.getBlogList(sysUser,article,page,true);
    }

    //彻底删除博客
    @ResponseBody
    @Route(value = "/article/delete/delete",method = Route.HttpMethod.POST)
    public CheckResult<Void> removeArticle(@BindParam("id") Integer id,
                                           @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class) SysUser sysUser){
        return blogService.removeBlog(id,sysUser);
    }

    //恢复博客
    @ResponseBody
    @Route(value = "/article/delete/recovery",method = Route.HttpMethod.POST)
    public CheckResult<Void> recoveryArticle(@BindParam(value = "id",required = true) Integer id,
                                           @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class) SysUser sysUser){
        return blogService.recoveryBlog(id,sysUser);
    }

    @Route("/article/edit")
    public String editBlog(@BindParam("id") Integer id,
    @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class) SysUser sysUser
    ,Model model){
        model.setAttribute("blog",blogDao.selectUserBlogById(id,sysUser.getId()));
        return "blog/editArticle";
    }

    //修改博客
    @ResponseBody
    @Route(value = "/article/edit",method = Route.HttpMethod.POST)
    public CheckResult<Integer> editBlog(@JsonRequestBody Article article,
                                               @BindParam(value = ContentString.USER_SESSION_TAG,
                                                       from = HttpSession.class) SysUser user
            , HttpServletRequest request){
        return blogService.editBlog(article,user,request.getRemoteAddr());
    }

    @Route(("/comment"))
    public String toComment(){
        return "blog/comment_list";
    }

    @ResponseBody
    @Route(value = "/comment/list",method = Route.HttpMethod.POST)
    public TableData<List<Comment>> getCommentList(@BindParam(value = ContentString.USER_SESSION_TAG,
            from = HttpSession.class) SysUser user,@BindParam("keyword") String keyword,
                                                   @BindParam("page") Integer page,
                                                   @BindParam("showType")Integer showType){
        return blogService.getCommentsList(user,keyword,page,showType);
    }

    @ResponseBody
    @Route(value = "/comment/delete",method = Route.HttpMethod.POST)
    public CheckResult<Void> deleteComment(@BindParam(value = ContentString.USER_SESSION_TAG,
            from = HttpSession.class) SysUser user,
                                           @BindParam(value = "id",required = true) Integer id){
        return blogService.deleteComment(user,id);
    }

    //退出登录
    @Route(value = "/exit")
    public String exitLogin(HttpSession session){
        session.invalidate();
        return "redirect:"+ FrameworkConfig.contentPath+ "/user/";
    }

    @Route("/editSelf")
    public String editUserInfo(){
        return "user/edit_user_info";
    }

    @ResponseBody
    @Route(value = "/editSelf",method = Route.HttpMethod.POST)
    public CheckResult<Void> editUserInfo(@BindParam(value = ContentString.USER_SESSION_TAG,
            from = HttpSession.class) SysUser user,@JsonRequestBody SysUser uploadUser){
        return userService.editUserInfo(user,uploadUser);
    }

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
        return "redirect:"+FrameworkConfig.contentPath+ "/user/manage/dashboard";
    }

    @Route(value = "/dashboard",method = {Route.HttpMethod.GET})
    public String dashboard(){
        return "user/dashboard";
    }

    //后台首页
    @Route(value = "/welcome",method = Route.HttpMethod.GET)
    public String welcome(Model model,@BindParam(value = ContentString.USER_SESSION_TAG,
            from = HttpSession.class) SysUser user) throws ParseException {
        model.setAttribute("nowTime",commonService.getHourWelcome(null));
        model.setAttribute("operations",commonService.getUserOperations(user,null));
        model.setAttribute("blogCount",userService.getUserBlogCount(user.getId()));
        model.setAttribute("recentBlogs",userService.getUserRecentBlogs(user));
        model.setAttribute("allVisit",blogDao.getUserAllVisitCount(user.getId()));
        model.setAttribute("allComment",commentDao.getUserAllCommentCount(user.getId()));
        model.setAttribute("recentComments",commentDao.selectUserRecentComments(user.getId()));
        model.setAttribute("createDays", TimeUtils.getDays(user.getCreateTime()));
        return "user/welcome";
    }

    @ResponseBody
    @Route("/menu")
    public MenuModel getUserMenu(){
        return userService.getUserMenuData();
    }

}