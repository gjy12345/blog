package cn.gjy.blog.dao;

import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.Dao;
import cn.gjy.blog.framework.annotation.Select;
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
}
