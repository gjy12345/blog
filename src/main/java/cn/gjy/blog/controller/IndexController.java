package cn.gjy.blog.controller;

import cn.gjy.blog.framework.annotation.Controller;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.service.ArticleService;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class IndexController
 */
@Controller
public class IndexController {

    @InitObject
    private ArticleService articleService;

    //首页 显示推荐
    @Route(value = "/",method = {Route.HttpMethod.GET, Route.HttpMethod.POST})
    public String index(Model model){
        return "index";
    }
}
