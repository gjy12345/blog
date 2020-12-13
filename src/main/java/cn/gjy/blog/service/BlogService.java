package cn.gjy.blog.service;

import cn.gjy.blog.model.*;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/10
 * @Class BlogService
 */
public interface BlogService {

    CheckResult<Integer> releaseNewBlog(Article article, SysUser sysUser,String ip);

    TableData<List<Category>> getUserCategories(Integer page, SysUser sysUser, Category category);

    CheckResult<Void> addUserCategory(SysUser sysUser, Category category,String ip);

    CheckResult<Void> deleteCategory(Integer id, SysUser sysUser);

    CheckResult<Void> editCategory(Category category, SysUser sysUser);

    CheckResult<Void> lockOrUnlock(Integer id, Integer lock, SysUser user);
}
