package cn.gjy.blog.controller;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.model.DetailedArticle;
import cn.gjy.blog.model.DetailedSysUser;
import cn.gjy.blog.model.SysNotice;
import cn.gjy.blog.model.TableData;
import cn.gjy.blog.service.AdminService;
import cn.gjy.blog.service.ArticleService;
import cn.gjy.blog.service.UserService;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class IndexController
 */
@Controller
public class IndexController {

    @InitObject
    private ArticleService articleService;

    @InitObject
    private AdminService adminService;

    @InitObject
    private UserService userService;

    //首页 显示推荐
    @Route(value = "/",method = {Route.HttpMethod.GET, Route.HttpMethod.POST})
    public String index(Model model){
        model.setAttribute("child_jsp", FrameworkConfig.getJspPath("index"));
        model.setAttribute("recent_blog",articleService.selectBestBlogs());
        return "base";
    }

    @Route(value = "/new",method = {Route.HttpMethod.GET})
    public String newBlogs(Model model){
        model.setAttribute("child_jsp", FrameworkConfig.getJspPath("new"));
        model.setAttribute("recent_blog",articleService.selectRecentBlogs());
        return ContentString.BASE_JSP;
    }

    @Route("/notice")
    public String notice(Model model,@BindParam("id") Integer id){
        SysNotice sysNotice=adminService.getNoticeById(id);
        model.setAttribute("child_jsp", FrameworkConfig.getJspPath(sysNotice==null?"404":"notice"));
        model.setAttribute("notice",sysNotice);
        return ContentString.BASE_JSP;
    }

    @Route("/search")
    public String search(@BindParam("keyword") String keyword,Model model){
        model.setAttribute("keyword",keyword);
        model.setAttribute(ContentString.CHILD_JSP,FrameworkConfig.getJspPath("search"));
        return ContentString.BASE_JSP;
    }

    @ResponseBody
    @Route(value = "/search/article",method ={ Route.HttpMethod.POST, Route.HttpMethod.GET})
    public TableData<List<DetailedArticle>> searchArticle(@BindParam(value = "keyword",required = true) String keyword
    ,@BindParam("page") Integer page){
        return articleService.searchArticle(keyword,page);
    }

    @ResponseBody
    @Route(value = "/search/user",method ={ Route.HttpMethod.POST, Route.HttpMethod.GET})
    public TableData<List<DetailedSysUser>> searchUser(@BindParam(value = "keyword",required = true) String keyword
            ,@BindParam("page") Integer page){
        return userService.searchUser(keyword,page);
    }

}
