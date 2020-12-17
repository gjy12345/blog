package cn.gjy.blog.dao;

import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.Dao;
import cn.gjy.blog.framework.annotation.Select;
import cn.gjy.blog.framework.annotation.Update;
import cn.gjy.blog.model.Article;
import cn.gjy.blog.model.MenuModel;
import cn.gjy.blog.model.SysUser;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/8
 * @Class UserDao
 */
@Dao
public interface UserDao {

    @Select("select * from sys_menu where type=1;")
    List<MenuModel.MenuData> selectUserMenuData();

    @Select("select * from sys_user where username=#{username} and user_type=#{type}")
    SysUser selectUserByUsername(@BindParam("username") String username,@BindParam("type") int user);

    @Select("select count(*) from article where user_id=#{id} " +
            "and status<>4 and status<>2")
    Integer selectUserBlogCountById(@BindParam("id") Integer id);

    @Update("update sys_user set last_login_ip=#{ip}," +
            "last_login_time=#{time} where id=#{id};")
    int updateUserLastLogin(@BindParam("ip") String remoteIp, @BindParam("time") String time,@BindParam("id") Integer id);

    @Update("update sys_user set nickname=#{nickname}," +
            "sign=#{sign},password=#{password},face=#{face} where id=#{id}")
    int updateUserInfo(SysUser uploadUser);

    @Select("select * from sys_user where id=#{id}")
    SysUser selectUserById(@BindParam("id") Integer userId);

    @Select("select count(*) from follow where user_id=#{see} and follow_id=#{beSee}")
    int checkFollow(@BindParam("see") Integer see,@BindParam("beSee") Integer beSee);
}
