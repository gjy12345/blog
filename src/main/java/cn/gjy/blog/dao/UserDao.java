package cn.gjy.blog.dao;

import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.model.*;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/8
 * @Class UserDao
 */
@Dao
public interface UserDao {

    @Select("select * from sys_menu where type=#{userType};")
    List<MenuModel.MenuData> selectUserMenuData(@BindParam("userType") Integer userType);

    @Select("select * from sys_user where username=#{username} and user_type=#{type}")
    SysUser selectUserByUsername(@BindParam("username") String username,@BindParam("type") int user);

    @Select("select count(*) from article where user_id=#{id} " +
            "and status<>4 and status<>2")
    Integer selectUserBlogCountById(@BindParam("id") Integer id);

    @Update("update sys_user set last_login_ip=#{ip}," +
            "last_login_time=#{time} where id=#{id};")
    int updateUserLastLogin(@BindParam("ip") String remoteIp, @BindParam("time") String time,@BindParam("id") Integer id);

    @Update("update sys_user set nickname=#{nickname}," +
            "sign=#{sign},password=#{password},face=#{face},sex=#{sex} where id=#{id}")
    int updateUserInfo(SysUser uploadUser);

    @Select("select * from sys_user where id=#{id} and user_type=#{userType}")
    SysUser selectUserById(@BindParam("id") Integer userId,@BindParam("userType") Integer userType);


    @Select("select create_time from article where user_id=#{id} order by id limit 1")
    String getUserLastReleaseTime(@BindParam("id") Integer id);

    @Insert("INSERT INTO `sys_user` ( `username`, `password`, `nickname`, `lock`, `last_login_time`, `last_login_ip`, `user_type`, `face`, `sign`,  `create_time`, `sex` ) " +
            "VALUES(#{username},#{password},#{nickname},#{lock},null,null,#{user_type},#{face},#{sign},#{create_time},#{sex})")
    int addNewUser(SysUser sysUser);

    @Update("update sys_user set username=#{username},password=#{password}," +
            "nickname=#{nickname},sys_user.lock=#{lock},sex=#{sex},sign=#{sign} where id=#{id}")
    int updateUser(SysUser uploadUser);

    @Select("select * from sys_notice where sys_notice.show=1 order by id desc")
    List<SysNotice> selectShowNotices();

    @Select("select count(*) from sys_user where nickname like concat('%',#{keyword},'%')")
    int getUserCountByNickname(@BindParam("keyword") String keyword);

    @Select("select * from sys_user where nickname like concat('%',#{keyword},'%') limit #{s},#{e} ")
    List<DetailedSysUser> selectUserListByNickname(@BindParam("keyword")String keyword,@BindParam("s")Integer s,
                                                   @BindParam("e")Integer e);
}
