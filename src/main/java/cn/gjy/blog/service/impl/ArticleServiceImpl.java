package cn.gjy.blog.service.impl;

import cn.gjy.blog.dao.ArticleDao;
import cn.gjy.blog.dao.CommentDao;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Service;
import cn.gjy.blog.model.Article;
import cn.gjy.blog.model.DetailedArticle;
import cn.gjy.blog.service.ArticleService;

import java.util.List;
import java.util.function.Consumer;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class ArticleServiceImpl
 */
@Service(ArticleService.class)
public class ArticleServiceImpl implements ArticleService {

    @InitObject
    private ArticleDao articleDao;

    @InitObject
    private CommentDao commentDao;

    @Override
    public List<DetailedArticle> selectRecentBlogs() {
        List<DetailedArticle> articles=articleDao.selectRecentBlog(0,10);
        articles.forEach(article -> {
           if(article.getPublicityLevel()!=null){
               switch (article.getPublicityLevel()){
                   case Article.PubLevel.PUBLIC:
                       article.setPubLevelName("公开");
                       break;
                   case Article.PubLevel.PRIVATE:
                       article.setPubLevelName("私密");
                       break;
                   case Article.PubLevel.PASSWORD:
                       article.setPubLevelName("需要密码");
                       break;
               }
           }
           article.setCommon(commentDao.selectArticleCommentsCount(article.getId()));
           article.setUp(0);
           article.setVisit(0);
           if(article.getType()==null)
               article.setTypeName("未分类");
        });
        return articles;
    }
}
