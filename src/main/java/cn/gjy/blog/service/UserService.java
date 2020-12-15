package cn.gjy.blog.service;

import cn.gjy.blog.model.Article;
import cn.gjy.blog.model.CheckResult;
import cn.gjy.blog.model.MenuModel;
import cn.gjy.blog.model.SysUser;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class ArticleService
 * 用户的除文章外其他操作
 */
public interface UserService {

    MenuModel getUserMenuData();

    CheckResult<SysUser> loginUser(SysUser sysUser);

    SysUser getTestUser();

    Integer getUserBlogCount(SysUser user);

    List<Article> getUserRecentBlogs(SysUser user);

    CheckResult<Void> editUserInfo(SysUser user, SysUser uploadUser);
}
