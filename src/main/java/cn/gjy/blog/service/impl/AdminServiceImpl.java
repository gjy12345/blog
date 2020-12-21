package cn.gjy.blog.service.impl;

import cn.gjy.blog.common.ContentString;
import cn.gjy.blog.dao.*;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Service;
import cn.gjy.blog.framework.controller.HttpRequestUtil;
import cn.gjy.blog.listener.SessionListener;
import cn.gjy.blog.model.*;
import cn.gjy.blog.service.AdminService;
import cn.gjy.blog.service.UserService;
import cn.gjy.blog.utils.StringUtils;
import cn.gjy.blog.utils.TimeUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/20
 * @Class AdminServiceImpl
 */
@Service(AdminService.class)
public class AdminServiceImpl implements AdminService{

    @InitObject
    private AdminDao adminDao;

    @InitObject
    private UserDao userDao;

    @InitObject
    private UserService userService;

    @InitObject
    private BlogDao blogDao;

    @InitObject
    private CommonDao commonDao;

    @InitObject
    private CommentDao commentDao;

    @Override
    public Integer getUserCount() {
        return adminDao.getUserCount(null,null);
    }

    @Override
    public Integer getBlogCount() {
        return adminDao.getBlogCount(new Article());
    }

    @Override
    public Integer getCommentCount() {
        return adminDao.getCommentCount(null,null);
    }

    @Override
    public TableData<List<DetailedSysUser>> getUserTableData(SysUser sysUser, Integer page,String keyword) {
        TableData<List<DetailedSysUser>> tableData=new TableData<>();
        tableData.setTotal(adminDao.getUserCount(sysUser,keyword));
        tableData.setData(adminDao.selectUserList(sysUser,keyword,page==null?0:--page,10));
        tableData.getData().forEach(detailedSysUser -> {
            detailedSysUser.setBlogCount(userService.getUserBlogCount(detailedSysUser.getId()));
            detailedSysUser.setOnline(SessionListener.checkUserOnline(detailedSysUser));
            detailedSysUser.setSexName(ContentString.MAN.equals(detailedSysUser.getSex())?"男":"女");
            detailedSysUser.setLastRelease(userDao.getUserLastReleaseTime(detailedSysUser.getId()));
        });
        return tableData;
    }

    @Override
    public CheckResult<Void> addNewUser(SysUser uploadUser) {
        CheckResult<Void> checkResult=checkUserInfo(uploadUser);
        if(checkResult!=null)
            return checkResult;
        if(userDao.selectUserByUsername(uploadUser.getUsername(),SysUser.USER)!=null)
            return CheckResult.createFailResult("该账号已存在!");
        uploadUser.setUserType(SysUser.USER);
        uploadUser.setCreateTime(TimeUtils.getSimpleDateFormat().format(new Date()));
        commonDao.insertLog(HttpRequestUtil.getAdminUserId(), SysOperation.OperationType.ADD_NEW_USER,
                "添加新用户:"+uploadUser.getUsername(), TimeUtils.getSimpleDateFormat().format(new Date()),
                HttpRequestUtil.getRemoteIp(),"web");
        userDao.addNewUser(uploadUser);
        return CheckResult.createSuccessResult(null,"新增用户成功");
    }

    @Override
    public SysUser getUserById(Integer userId) {
        if(userId==null)
            return null;
        return userDao.selectUserById(userId,SysUser.USER);
    }

    @Override
    public CheckResult<Void> editUser(SysUser uploadUser) {
        CheckResult<Void> checkResult=checkUserInfo(uploadUser);
        if(checkResult!=null)
            return checkResult;
        //判断是否为非法的用户id
        if(uploadUser.getId()==null||userDao.selectUserById(uploadUser.getId(),SysUser.USER)==null){
            return CheckResult.createFailResult("用户id错误");
        }
        SysUser dbUser = userDao.selectUserByUsername(uploadUser.getUsername(), SysUser.USER);
        if(!dbUser.getId().equals(uploadUser.getId())){
            return CheckResult.createFailResult("该账号已存在!");
        }
        uploadUser.setUserType(SysUser.USER);
        commonDao.insertLog(HttpRequestUtil.getAdminUserId(), SysOperation.OperationType.EDIT_NEW_USER,
                "修改用户:"+uploadUser.getUsername()+" 到:"+uploadUser.getUsername(),
                TimeUtils.getSimpleDateFormat().format(new Date()),
                HttpRequestUtil.getRemoteIp(),"web");
        userDao.updateUser(uploadUser);
        return CheckResult.createSuccessResult(null,"修改用户成功");
    }

    @Override
    public CheckResult<Void> deleteUserById(Integer id) {
        //删除用户信息，用户评论，用户文章，用户分类，用户日志
        if(id==null||userDao.selectUserById(id,SysUser.USER)==null)
            return CheckResult.createFailResult("非法的用户编号");
        blogDao.deleteBlogByUserId(id);
        blogDao.deleteCategoryByUserId(id);
        commentDao.deleteCommentByUserId(id);
        commonDao.deleteOperationByUserId(id);
        adminDao.deleteUserById(id);
        return CheckResult.createSuccessResult(null,"删除成功");
    }

    @Override
    public TableData<List<DetailedArticle>> getBlogList(Article article, Integer page, boolean b) {
        TableData<List<DetailedArticle>> tableData = new TableData<>();
        tableData.setTotal(blogDao.selectUserBlogsCount(article,null));
        tableData.setData(blogDao.selectUserBlogsByArgs(article, page==null?0:--page, 10,null));
        tableData.getData().forEach(detailedArticle -> {
            detailedArticle.setCommon(commentDao.selectArticleCommentsCount(detailedArticle.getId()));
            detailedArticle.setUp(0);
            SysUser dbUser = userDao.selectUserById(detailedArticle.getUserId(), SysUser.USER);
            if (dbUser!=null) {
                detailedArticle.setUserName(dbUser.getNickname());
                detailedArticle.setLoginUsername(dbUser.getUsername());
            }
            if (detailedArticle.getUpdateTime() == null)
                detailedArticle.setUpdateTime(detailedArticle.getCreateTime());
            if(detailedArticle.getTypeName()==null){
                detailedArticle.setTypeName("未分类");
            }
            if (detailedArticle.getPublicityLevel() == null) {
                return;
            }
            switch (detailedArticle.getPublicityLevel()) {
                case Article.PubLevel.PASSWORD:
                    detailedArticle.setPubLevelName("密码");
                    break;
                case Article.PubLevel.PRIVATE:
                    detailedArticle.setPubLevelName("私密");
                    break;
                case Article.PubLevel.PUBLIC:
                    detailedArticle.setPubLevelName("公开");
                    break;
            }
        });
        return tableData;
    }

    @Override
    public CheckResult<Void> deleteBlogById(Integer id) {
        Article article=blogDao.selectBlogByIdAllStatus(id);
        if(article==null)
            return CheckResult.createFailResult("删除失败,该博客不存在!");
        if(article.getStatus()==Article.ArticleState.DELETE||
            article.getStatus()==Article.ArticleState.LOCK){
            blogDao.removeBlog(id);//彻底删除
        }else {
            blogDao.deleteBlog(id);//移入回收站
        }
        commonDao.insertLog(HttpRequestUtil.getAdminUserId(), SysOperation.OperationType.EDIT_NEW_USER,
                "删除博客:"+article.getTitle(),
                TimeUtils.getSimpleDateFormat().format(new Date()),
                HttpRequestUtil.getRemoteIp(),"web");
        return CheckResult.createSuccessResult(null,"删除成功");
    }

    @Override
    public CheckResult<Void> lockOrUnlockBlog(Integer id, Integer changeStatus) {
        Article article=blogDao.selectBlogById(id);
        if(article==null)
            return CheckResult.createFailResult("找不到此文章");
        if(changeStatus==Article.ArticleState.LOCK){
            //可以修改
            if(article.getStatus()==Article.ArticleState.RELEASE){
                //锁定
                adminDao.lockOrUnlockBlog(id,changeStatus);
                commonDao.insertLog(HttpRequestUtil.getAdminUserId(), SysOperation.OperationType.LOCK_BLOG,
                        "锁定博客:"+article.getTitle(),
                        TimeUtils.getSimpleDateFormat().format(new Date()),
                        HttpRequestUtil.getRemoteIp(),"web");
                return CheckResult.createSuccessResult(null,"操作成功");
            }else if(article.getStatus()==Article.ArticleState.LOCK){
                return CheckResult.createFailResult("此博客已被锁定");
            }else
                return CheckResult.createFailResult("该博客无法被锁定");
        }else if(changeStatus==Article.ArticleState.RELEASE){
            if(article.getStatus()!=Article.ArticleState.LOCK){
                return CheckResult.createFailResult("此博客状态不是锁定");
            }else {
                //解锁
                adminDao.lockOrUnlockBlog(id,changeStatus);
                commonDao.insertLog(HttpRequestUtil.getAdminUserId(), SysOperation.OperationType.UNLOCK_BLOG,
                        "解锁博客:"+article.getTitle(),
                        TimeUtils.getSimpleDateFormat().format(new Date()),
                        HttpRequestUtil.getRemoteIp(),"web");
                return CheckResult.createSuccessResult(null,"操作成功");
            }
        }
        return CheckResult.createFailResult("操作失败");
    }

    @Override
    public TableData<List<Comment>> getCommentListData( Integer page, String keyword) {
        page=page==null?0:page-1;
        TableData<List<Comment>> tableData=new TableData<>();
        tableData.setTotal(blogDao.getUserBlogCommentCount(null,keyword,null));
        tableData.setData(blogDao.selectUserBlogCommentList(null,keyword,page,10,null));
        return tableData;
    }

    @Override
    public CheckResult<Void> deleteCommentById(Integer id) {
        return commentDao.deleteComment(id)==1?CheckResult.createSuccessResult(null,"删除成功")
                :CheckResult.createFailResult("删除失败");
    }

    private CheckResult<Void> checkUserInfo(SysUser uploadUser){
        if(StringUtils.isEmptyString(uploadUser.getUsername())){
            return CheckResult.createFailResult("用户名不能为空!");
        }
        if(StringUtils.isEmptyString(uploadUser.getNickname())){
            return CheckResult.createFailResult("昵称不能为空!");
        }
        if(StringUtils.isEmptyString(uploadUser.getPassword())){
            return CheckResult.createFailResult("密码不能为空!");
        }
        if(uploadUser.getSex()==null)
            return CheckResult.createFailResult("性别为空!");
        if(uploadUser.getLock()==null)
            return CheckResult.createFailResult("锁定参数错误!");
        if(uploadUser.getUsername().equalsIgnoreCase("admin")){
            return CheckResult.createFailResult("非法账号名");
        }
        return null;
    }

}
