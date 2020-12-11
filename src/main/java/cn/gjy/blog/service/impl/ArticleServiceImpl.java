package cn.gjy.blog.service.impl;

import cn.gjy.blog.dao.ArticleDao;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Service;
import cn.gjy.blog.model.Article;
import cn.gjy.blog.service.ArticleService;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class ArticleServiceImpl
 */
@Service(ArticleService.class)
public class ArticleServiceImpl implements ArticleService {

    @InitObject
    private ArticleDao articleDao;

    @Override
    public List<Article> selectRecentBlogs() {
        return articleDao.selectRecentBlog(0,10);
    }
}
