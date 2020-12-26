package cn.gjy.blog.service.impl;

import cn.gjy.blog.dao.ArticleDao;
import cn.gjy.blog.dao.CommentDao;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Service;
import cn.gjy.blog.model.Article;
import cn.gjy.blog.model.DetailedArticle;
import cn.gjy.blog.model.TableData;
import cn.gjy.blog.service.ArticleService;
import cn.gjy.blog.service.BlogService;
import cn.gjy.blog.utils.StringUtils;

import java.util.ArrayList;
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

    @InitObject
    private BlogService blogService;

    @Override
    public List<DetailedArticle> selectRecentBlogs() {
        List<DetailedArticle> articles=articleDao.selectRecentBlog(0,10);
        setArticleInfo(articles);
        articles.forEach(article -> article.setCommon(commentDao.selectArticleCommentsCount(article.getId())));
        return articles;
    }

    @Override
    public List<DetailedArticle> selectBestBlogs() {
        List<DetailedArticle> articles=articleDao.selectBestBlogs(0,10);
        setArticleInfo(articles);
        return articles;
    }

    @Override
    public TableData<List<DetailedArticle>> searchArticle(String keyword,Integer page) {
        if(StringUtils.isEmptyString(keyword)){
            return TableData.emptyData(new ArrayList<>());
        }
        TableData<List<DetailedArticle>> tableData=new TableData<>();
        tableData.setData(articleDao.selectBlogsByKey(keyword,(page==null?0:--page)*10,10));
        tableData.setTotal(articleDao.getSearchBlogCount(keyword));
        setArticleInfo(tableData.getData());
        tableData.getData().forEach(article -> article.setCommon(commentDao.selectArticleCommentsCount(article.getId())));
        return tableData;
    }

    private void setArticleInfo(List<DetailedArticle> articles){
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
            if(article.getType()==null)
                article.setTypeName("未分类");
        });
    }
}
