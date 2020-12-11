package cn.gjy.blog.dao;

import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.Dao;
import cn.gjy.blog.framework.annotation.Select;
import cn.gjy.blog.model.Article;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/11
 * @Class ArticleDao
 */
@Dao
public interface ArticleDao {

    @Select("select* from article where  " +
            "status=7 or status=6 " +
            "order by id desc limit #{start},#{end}")
    List<Article> selectRecentBlog(@BindParam("start") int start, @BindParam("end") int end);
}
