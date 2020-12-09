package cn.gjy.blog.controller.user;

import cn.gjy.blog.framework.annotation.Controller;
import cn.gjy.blog.framework.annotation.Route;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class BlogController
 */
@Route("/user/manage")
@Controller
public class BlogManageController {

    @Route(value = "/article/new",method = Route.HttpMethod.GET)
    public String writeNewBlog(){
        return "blog/writeArticle";
    }
}
