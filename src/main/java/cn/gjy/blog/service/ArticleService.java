package cn.gjy.blog.service;


import cn.gjy.blog.model.Article;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class ArticleService
 * 用户的关于文章的操作
 */
public interface ArticleService {


    List<Article> selectRecentBlogs();
}
