package cn.gjy.blog.dao;

import cn.gjy.blog.dao.method.admin.AdminBlogMethodSql;
import cn.gjy.blog.dao.method.admin.AdminCommentMethodSql;
import cn.gjy.blog.dao.method.admin.AdminUserMethodSql;
import cn.gjy.blog.framework.Invocation.DaoInvocationHandlerImpl;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.model.Article;
import cn.gjy.blog.model.DetailedSysUser;
import cn.gjy.blog.model.SysUser;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/20
 * @Class AdminDao
 */
@Dao
public interface AdminDao {

    @UseCustomMethod(value = AdminUserMethodSql.UserMethodCount.class,sqlType = DaoInvocationHandlerImpl.SqlType.SELECT)
    Integer getUserCount(SysUser sysUser,String keyword);

    @UseCustomMethod(value = AdminBlogMethodSql.BlogCountMethod.class,sqlType = DaoInvocationHandlerImpl.SqlType.SELECT)
    Integer getBlogCount(Article article);

    @UseCustomMethod(value = AdminCommentMethodSql.CommentCountMethod.class,sqlType = DaoInvocationHandlerImpl.SqlType.SELECT)
    Integer getCommentCount(Integer userId,String keyword);

    @UseCustomMethod(value = AdminUserMethodSql.UserMethodData.class,sqlType = DaoInvocationHandlerImpl.SqlType.SELECT)
    List<DetailedSysUser> selectUserList(SysUser sysUser,String keyword, Integer page, Integer size);

    @Delete("delete from sys_user where id=#{id}")
    int deleteUserById(@BindParam("id") Integer id);

    @Update("update article set article.status=#{status} where id=#{id}")
    int lockOrUnlockBlog(@BindParam("id") Integer id,@BindParam("status") Integer changeStatus);
}
