package cn.gjy.blog.controller.user;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.dao.BlogDao;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.model.*;
import cn.gjy.blog.service.BlogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String choseOptions(@BindParam("blogId") String blogId,Model model,
                              @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class)
                                      SysUser sysUser){
        Category category=new Category();
        category.setCreateUser(sysUser.getId());
        model.setAttribute("categories",blogDao.getUserBlogCategories(category,null,null));
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
        return "user/blog_category";
    }

    @Route("/article/type/add")
    public String toAddCategory(){
        return "user/blog_category_add";
    }

    @ResponseBody
    @Route(value = "/article/type/add",method = Route.HttpMethod.POST)
    public CheckResult<Void> addCategory(@JsonRequestBody Category category,
                                         @BindParam(value = ContentString.USER_SESSION_TAG,from = HttpSession.class)
                                                 SysUser sysUser,HttpServletRequest request){
        return blogService.addUserCategory(sysUser,category,request.getRemoteAddr());
    }
}
