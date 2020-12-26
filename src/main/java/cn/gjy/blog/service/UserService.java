package cn.gjy.blog.service;

import cn.gjy.blog.model.*;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class ArticleService
 * 用户的除文章外其他操作
 */
public interface UserService {

    MenuModel getUserMenuData(Integer userType);

    CheckResult<SysUser> loginUser(SysUser sysUser,Integer type);

    SysUser getTestUser();

    Integer getUserBlogCount(Integer userId);

    List<Article> getUserRecentBlogs(SysUser user);

    CheckResult<Void> editUserInfo(SysUser user, SysUser uploadUser);

    SysUser getUserInfo(Integer userId);

    TableData<List<Category>> getUserCategories(Integer userId, Integer page);

    SysUser getTestAdminUser();

    List<SysNotice> getShowNotices();

    TableData<List<DetailedSysUser>> searchUser(String keyword, Integer page);
}
