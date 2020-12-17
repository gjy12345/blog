package cn.gjy.blog.controller.blog;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.dao.BlogDao;
import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.Controller;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.model.Article;
import cn.gjy.blog.model.Category;
import cn.gjy.blog.model.TableData;
import cn.gjy.blog.service.BlogService;

/**
 * @Author gujianyang
 * @Date 2020/12/11
 * @Class BlogController
 */
@Route("/article")
@Controller
public class BlogController {

    @InitObject
    private BlogService blogService;
    private BlogDao blogDao;

    @Route("/detail")
    public String blogDetail(Model model,@BindParam("url") String url,@BindParam("password") String password){

        String toUrl=blogService.selectBlogDetailsByUrl(url,password,model);
        model.setAttribute(ContentString.CHILD_JSP, FrameworkConfig.getJspPath(toUrl));
        model.setAttribute("url",url);
        return ContentString.BASE_JSP;
    }


    //排行
    @Route("/ranking")
    public String ranking(Model model){
        model.setAttribute(ContentString.CHILD_JSP,FrameworkConfig.getJspPath("blog/blog_ranking"));
        blogService.setRankingData(model);
        return ContentString.BASE_JSP;
    }

}
