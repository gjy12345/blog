package cn.gjy.blog.controller;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.framework.annotation.Controller;
import cn.gjy.blog.framework.annotation.Route;
import cn.gjy.blog.framework.model.Model;

/**
 * @Author gujianyang
 * @Date 2020/12/11
 * @Class BlogController
 */
@Route("/blog")
@Controller
public class BlogController {

    @Route("/detail")
    public String blogDetail(Model model){
        model.setAttribute(ContentString.CHILD_JSP,"blog-detail");
        return ContentString.BASE_JSP;
    }


}
