package cn.gjy.blog.dao;

import cn.gjy.blog.dao.method.CategoryMethodSql;
import cn.gjy.blog.framework.Invocation.DaoInvocationHandlerImpl;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.model.Article;
import cn.gjy.blog.model.Category;

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
            " `type`,  `publicity_level`,  `user_id`,time_stamp   )  " +
            "VALUES  (  #{create_time},  #{update_time},  #{comment},  #{content},  " +
            "#{description},  #{keywords},  #{password},  #{status},  #{thumb},  #{title},  #{top_priority}, " +
            " #{url},  #{type},  #{publicity_level},  #{user_id} ,#{time_stamp}  );")
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

    @Select("select * from category where id=#{id}")
    Category selectUserCategoryById(@BindParam("id") Integer id);

    @Select("select count(*) from article where type=#{type}")
    int getBlogCountByCategory(@BindParam("type") Integer id);
}
