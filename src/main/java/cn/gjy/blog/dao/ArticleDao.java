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

    @Select("SELECT article.*,category.`name` as typeName,sys_user.nickname as userName,sys_user.face,ifnull(t1.cnum,0) as common from article \n" +
            "left join category \n" +
            "on category.id=article.type \n" +
            "left join sys_user \n" +
            "on sys_user.id=article.user_id\n" +
            "left join (select article_id,count(*) as cnum from `comment` GROUP BY `comment`.article_id) as t1\n" +
            "on t1.article_id=article.id\n" +
            " where publicity_level<>5 and article.status=1 order by article.visit desc,t1.cnum desc,article.id desc " +
            " limit #{start},#{end}")
    List<DetailedArticle> selectBestBlogs(@BindParam("start") int start, @BindParam("end") int end);


    @Select("SELECT article.id,\n" +
            "\tarticle.create_time,\n" +
            "\tarticle.update_time,\n" +
            "\tarticle.`comment`,\n" +
            "\tarticle.description,\n" +
            "\tarticle.keywords,\n" +
            "\tarticle.`password`,\n" +
            "\tarticle.`status`,\n" +
            "\tarticle.thumb,\n" +
            "\tarticle.title,\n" +
            "\tarticle.top_priority,\n" +
            "\tarticle.url,\n" +
            "\tarticle.type,\n" +
            "\tarticle.publicity_level,\n" +
            "\tarticle.user_id,\n" +
            "\tarticle.time_stamp,\n" +
            "\tarticle.visit ,category.`name` as typeName,sys_user.nickname as userName,sys_user.face from article " +
            "left join category " +
            "on category.id=article.type "+
            "left join sys_user " +
            "on sys_user.id=article.user_id where publicity_level<>5 and article.status=1 and (article.title like concat('%',#{keyword},'%') or article.keywords like concat('%',#{keyword},'%')) order by id desc  limit #{start},#{end}")
    List<DetailedArticle>  selectBlogsByKey(@BindParam("keyword") String keyword,@BindParam("start") int start, @BindParam("end") int end);

    @Select("select count(*) from article where publicity_level<>5 and article.status=1 and (article.title like concat('%',#{keyword},'%') or article.keywords like concat('%',#{keyword},'%'))")
    int getSearchBlogCount(@BindParam("keyword") String keyword);
}
