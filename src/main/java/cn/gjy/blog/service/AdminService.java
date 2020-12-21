package cn.gjy.blog.service;

import cn.gjy.blog.model.*;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class AdminService
 * 管理员操作
 */
public interface AdminService {

    Integer getUserCount();

    Integer getBlogCount();

    Integer getCommentCount();

    TableData<List<DetailedSysUser>> getUserTableData(SysUser sysUser, Integer page,String keyword);

    CheckResult<Void> addNewUser(SysUser sysUser);

    SysUser getUserById(Integer userId);

    CheckResult<Void> editUser(SysUser uploadUser);

    CheckResult<Void> deleteUserById(Integer id);

    TableData<List<DetailedArticle>> getBlogList(Article article, Integer page, boolean b);

    CheckResult<Void> deleteBlogById(Integer id);

    CheckResult<Void> lockOrUnlockBlog(Integer id, Integer changeStatus);

    TableData<List<Comment>> getCommentListData(Integer page, String keyword);

    CheckResult<Void> deleteCommentById(Integer id);
}
