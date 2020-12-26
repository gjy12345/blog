package cn.gjy.blog.controller.user;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.dao.BlogDao;
import cn.gjy.blog.dao.CommentDao;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.model.*;
import cn.gjy.blog.service.BlogService;
import cn.gjy.blog.service.CommonService;
import cn.gjy.blog.service.UserService;
import cn.gjy.blog.utils.TimeUtils;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
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

    @InitObject
    private BlogDao blogDao;

    @InitObject
    private CommentDao commentDao;

    @InitObject
    private BlogService blogService;

    @Route("/info")
    public String userInfo(@BindParam(value = "userId",required = true) Integer userId,Model model,
                           @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class)
                                   SysUser sysUser){
        model.setAttribute(ContentString.CHILD_JSP,FrameworkConfig.getJspPath("user/info"));
        SysUser userInfo = userService.getUserInfo(userId);
        if(userInfo==null){
            model.setAttribute(ContentString.CHILD_JSP,FrameworkConfig.getJspPath("404"));
        }else {
            model.setAttribute("showUser",userInfo);
            model.setAttribute("allVisit",blogDao.getUserAllVisitCount(userId));
            model.setAttribute("allArticlesCount",userService.getUserBlogCount(userId));
        }
        return ContentString.BASE_JSP;
    }

    @ResponseBody
    @Route("/categoryList")
    public TableData<List<Category>> getUserCategories(@BindParam(value = "userId",required = true)Integer userId,
                                             @BindParam("page") Integer page){
        return userService.getUserCategories(userId,page);
    }

    @ResponseBody
    @Route("/articleList")
    public TableData<List<DetailedArticle>> getUserArticles(@BindParam(value = "userId",required = true)Integer userId,
                                                 @BindParam("page") Integer page){
        return blogService.getUserPublicArticles(userId,page);
    }

    @ResponseBody
    @Route("/followUser")
    public CheckResult<Void> followUser(@BindParam(value = "userId",required = true) Integer userId){
        return null;
    }
}
