package cn.gjy.blog.dao;

import cn.gjy.blog.dao.method.admin.AdminBlogMethodSql;
import cn.gjy.blog.dao.method.admin.AdminCommentMethodSql;
import cn.gjy.blog.dao.method.admin.AdminUserMethodSql;
import cn.gjy.blog.framework.Invocation.DaoInvocationHandlerImpl;
import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.model.*;

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

    @Select("select count(*) from sys_notice;")
    int getNoticeCount();

    @Select("select count(*) from sys_notice where title like concat('%',#{keyword},'%')")
    int getNoticeCountByKeyword(@BindParam("keyword") String keyword);

    @Select("select * from sys_notice where title like concat('%',#{keyword},'%')  order by id desc limit #{page},#{size}")
    List<SysNotice> selectNoticeListByKeyword( @BindParam("keyword") String keyword,@BindParam("page") Integer page,@BindParam("size")Integer size);

    @Select("select * from sys_notice order by id desc limit #{page},#{size}")
    List<SysNotice> selectNoticeList(@BindParam("page") Integer page,@BindParam("size")Integer size);

    @Insert("insert into sys_notice(title,content,create_time,create_time_l) " +
            "values(#{title},#{content},#{create_time},#{create_time_l});")
    int insertNotice(SysNotice notice);

    @Select("select * from sys_notice where title = #{title}")
    SysNotice selectNoticeByName(@BindParam("title") String title);

    @Select("select * from sys_notice where id = #{id}")
    SysNotice selectNoticeById(@BindParam("id") Integer id);

    @Update("update sys_notice set content=#{content}," +
            "update_time=#{update_time}," +
            "title=#{title} where id=#{id}")
    int updateNotice(SysNotice notice);

    @Delete("delete from sys_notice where id=#{id}")
    int deleteNoticeById(@BindParam("id") Integer id);

    @Update("update sys_notice set sys_notice.show=#{show} where id=#{id}")
    int updateNoticeShow(@BindParam("id") Integer id,@BindParam("show") Integer show);

    @Update("update sys_user set nickname=#{nickname}," +
            "sign=#{sign},password=#{password},face=#{face},sex=#{sex} where id=#{id}")
    int updateAdminInfo(SysUser uploadUser);
}
