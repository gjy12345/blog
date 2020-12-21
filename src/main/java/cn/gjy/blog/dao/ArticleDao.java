package cn.gjy.blog.dao;

import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.Dao;
import cn.gjy.blog.framework.annotation.Select;
import cn.gjy.blog.model.Article;
import cn.gjy.blog.model.DetailedArticle;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/11
 * @Class ArticleDao
 */
@Dao
public interface ArticleDao {

    @Select("SELECT article.*,category.`name` as typeName,sys_user.nickname as userName,sys_user.face from article " +
            "left join category " +
            "on category.id=article.type "+
            "left join sys_user " +
            "on sys_user.id=article.user_id where publicity_level<>5 and article.status=1 order by id desc  limit #{start},#{end}")
    List<DetailedArticle> selectRecentBlog(@BindParam("start") int start, @BindParam("end") int end);
}
