package cn.gjy.blog.dao;

import cn.gjy.blog.dao.method.BlogMethodSql;
import cn.gjy.blog.dao.method.CategoryMethodSql;
import cn.gjy.blog.dao.method.CommentMethodSql;
import cn.gjy.blog.framework.Invocation.DaoInvocationHandlerImpl;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.model.*;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/10
 * @Class BlogDao
 */
@Dao
public interface BlogDao {

    @Insert("INSERT INTO `blog`.`article` (  `create_time`,  `update_time`,  `comment`,  `content`,  " +
            "`description`,  `keywords`,  `password`,  `status`,  `thumb`,  `title`,  `top_priority`,  `url`, " +
            " `type`,  `publicity_level`,  `user_id`,time_stamp,markdown   )  " +
            "VALUES  (  #{create_time},  #{update_time},  #{comment},  #{content},  " +
            "#{description},  #{keywords},  #{password},  #{status},  #{thumb},  #{title},  #{top_priority}, " +
            " #{url},  #{type},  #{publicity_level},  #{user_id} ,#{time_stamp},#{markdown}  );")
    int releaseNewBlog(Article article);

    @UseCustomMethod(value = CategoryMethodSql.CategoryCountMethod.class,
            sqlType = DaoInvocationHandlerImpl.SqlType.SELECT)
    int getUserBlogCategoriesCount(Category category);

    @UseCustomMethod(value = CategoryMethodSql.CategoryDataMethod.class,
            sqlType = DaoInvocationHandlerImpl.SqlType.SELECT)
    List<Category> getUserBlogCategories(Category category,Integer page,Integer pageSize);

    @Select("select count(*) from category where name=#{name} and create_user=#{userId}")
    int selectUserCategoryByName(@BindParam("name") String name,@BindParam("userId") Integer userId);

    @Insert("INSERT INTO category (  `name`, `lock`, `description`, `create_time`, `create_user`,  `time_stamp` )\n" +
            "VALUES\n" +
            "\t(  #{name}, #{lock}, #{description}, #{create_time}, #{create_user}, #{time_stamp} );")
    int addNewCategory(Category category);

    @Select("select* from article where user_id=#{id} " +
            "and status<>4 and status<>2 " +
            "order by id desc limit #{start},#{end}")
    List<Article> selectUserBlogs(@BindParam("id") Integer id,@BindParam("start") int s,@BindParam("end") int e);

    @Select("select * from category where id=#{id} and create_user=#{userId}")
    Category selectUserCategoryById(@BindParam("id") Integer id,@BindParam("userId") Integer userId);

    @Select("select count(*) from article where type=#{type}")
    int getBlogCountByCategory(@BindParam("type") Integer id);

    @Delete("delete from category where id=#{id}")
    int deleteCategoryById(@BindParam("id") Integer id);

    @Update("update article set type=null where type=#{type};")
    int resetBlogsCategoryByCategoryId(@BindParam("type") Integer id);

    @Update("update category set name=#{name},category.lock=#{lock},description=#{description}," +
            "update_time=#{update_time} where id=#{id};")
    int updateCategory(Category category);

    @Update("update category set category.lock=#{lock} where id=#{id};")
    int updateCategoryLock(@BindParam("id") Integer id,@BindParam("lock") Integer lock);

    @UseCustomMethod(value = BlogMethodSql.BlogCountMethod.class,
            sqlType = DaoInvocationHandlerImpl.SqlType.SELECT)
    int selectUserBlogsCount(Article article,Boolean isDelete);

    @UseCustomMethod(value = BlogMethodSql.BlogDataMethod.class,
            sqlType = DaoInvocationHandlerImpl.SqlType.SELECT)
    List<DetailedArticle> selectUserBlogsByArgs(Article article,Integer page,Integer size,Boolean isDelete);

    @Select("select * from article where id=#{id} and user_id=#{userId} and status<>4")
    Article selectUserBlogById(@BindParam("id") Integer id, @BindParam("userId") Integer userId);

    @Update("update article set status=4 where id=#{id}")
    int deleteBlog(@BindParam("id") Integer id);

    @Select("select * from article where id=#{id} and user_id=#{userId} and status=4")
    Article selectUserDeleteBlogById(@BindParam("id") Integer id,@BindParam("userId") Integer userId);

    @Delete("delete from article where id=#{id}")
    int removeBlog(@BindParam("id") Integer id);

    //不能恢复被锁定的博客
    @Update("update article set status =1 where id=#{id} and status=4")
    int recoveryBlog(@BindParam("id") Integer id);

    @Select("SELECT article.*,category.`name` as typeName,sys_user.nickname as userName,sys_user.face from article " +
            "left join category " +
            "on category.id=article.type "+
            "left join sys_user " +
            "on sys_user.id=article.user_id " +
            "where article.url=#{url}")
    DetailedArticle selectArticleByUrl(@BindParam("url") String url);

    @Update("UPDATE `blog`.`article` \n" +
            "SET " +
            "`update_time` = #{update_time},\n" +
            "`comment` = #{comment},\n" +
            "`content` = #{content},\n" +
            "`description` = #{description},\n" +
            "`keywords` = #{keywords},\n" +
            "`password` = #{password},\n" +
            "`thumb` = #{thumb},\n" +
            "`title` = #{title},\n" +
            "`type` = #{type},\n" +
            "`publicity_level` = #{publicity_level},\n" +
            "`markdown` = #{markdown} \n" +
            "WHERE\n" +
            "\t`id` = #{id};")
    int editBlog(Article article);

    @Select("select * from article where status=1 and publicity_level=6 or publicity_level=7 " +
            "and user_id=#{userId} order by id desc limit #{s},#{e}")
    List<Article> selectUserPubOrPwdBlog(@BindParam("userId") Integer userId, @BindParam("s") int s,@BindParam("e") int e);

    @Select("select * from article where id=#{id} and status<>4")
    Article selectBlogById(@BindParam("id") Integer id);

    @Select("select * from article where id=#{id}")
    Article selectBlogByIdAllStatus(@BindParam("id") Integer id);

    @UseCustomMethod(value = CommentMethodSql.CommentCountMethod.class
            ,sqlType = DaoInvocationHandlerImpl.SqlType.SELECT)
    int getUserBlogCommentCount(SysUser user, String keyword,Integer showType);

    @UseCustomMethod(value = CommentMethodSql.CommentDataMethod.class
            ,sqlType = DaoInvocationHandlerImpl.SqlType.SELECT)
    List<Comment> selectUserBlogCommentList(SysUser user, String keyword,Integer page,Integer size,Integer showType);

    @Update("update article set visit=visit+1 where id=#{id}")
    int updateVisit(@BindParam("id") Integer id);

    //获取所有文章访问量的总和
    @Select("SELECT COALESCE(sum(visit),0) from article where user_id=#{userId}")
    int getUserAllVisitCount(@BindParam("userId") Integer id);

    @Select("select count(*) from article where user_id=#{userId} and (publicity_level=6 or publicity_level=7)" +
            "and status=1")
    int getUserPublicBlogCount(@BindParam("userId") Integer userId);

    @Select("select article.*,category.name as typeName from article left join " +
            "category on category.id=article.type where user_id=#{userId} and (publicity_level=6 or publicity_level=7)" +
            "and status=1 order by id desc limit #{s},#{e}")
    List<DetailedArticle> selectUserPublicBlogs(@BindParam("userId") Integer userId,@BindParam("s")  int s,@BindParam("e")  int e);

    @Select("select article.title,article.url,category.name as typeName,sys_user.nickname as userName from article left join " +
            "category on category.id=article.type " +
            "left join sys_user on sys_user.id=article.user_id where (publicity_level=6 or publicity_level=7)" +
            "and status=1 order by article.visit desc limit 0,10")
    List<DetailedArticle> selectVisitRankingBlogs();

    @Select("SELECT sys_user.*,b.blogCount from (SELECT count(*) as blogCount,user_id from article GROUP BY user_id) as b \n" +
            "left join sys_user on sys_user.id=b.user_id\n" +
            "order by b.blogCount desc LIMIT 0,10;")
    List<SysUser> selectCzRanking();

    @Select("SELECT article.title,article.url,b.common FROM (SELECT article_id,COUNT(*) as common from `comment`\n" +
            "GROUP BY article_id ) as b\n" +
            "left join article on article.id=b.article_id\n" +
            "order by b.common desc \n" +
            "limit 0,10")
    List<DetailedArticle> selectCommentRanking();

    @Delete("delete from article where user_id=#{id}")
    int deleteBlogByUserId(@BindParam("id") Integer id);

    @Delete("delete from category where create_user=#{id}")
    int deleteCategoryByUserId(@BindParam("id") Integer id);
}
