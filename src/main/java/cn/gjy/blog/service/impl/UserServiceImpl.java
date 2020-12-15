package cn.gjy.blog.service.impl;

import cn.gjy.blog.dao.BlogDao;
import cn.gjy.blog.dao.CommonDao;
import cn.gjy.blog.dao.UserDao;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Service;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.controller.HttpRequestUtil;
import cn.gjy.blog.framework.tool.XssTool;
import cn.gjy.blog.model.*;
import cn.gjy.blog.service.UserService;
import cn.gjy.blog.utils.Md5Utils;
import cn.gjy.blog.utils.StringUtils;
import cn.gjy.blog.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author gujianyang
 * @Date 2020/12/8
 * @Class UserServiceImpl
 */
@Service(UserService.class)
public class UserServiceImpl implements UserService{

    @InitObject
    private UserDao userDao;

    @InitObject
    private BlogDao blogDao;

    @InitObject
    private CommonDao commonDao;

    @Override
    public MenuModel getUserMenuData() {
        List<MenuModel.MenuData> menuDataList=userDao.selectUserMenuData();
        List<MenuModel.MenuData> parentMenu=menuDataList.stream().filter(menuData -> menuData.getParentId()==null)
                .collect(Collectors.toList());
        parentMenu.forEach(menuData -> {
            List<MenuModel.MenuData> children=menuDataList.stream().filter(menuData1 ->
                    menuData.getId().equals(menuData1.getParentId()))
                    .sorted((o1, o2) -> o1.getId()-o2.getParentId()).collect(Collectors.toList());
            children.forEach(mData -> {
                mData.setUrl(FrameworkConfig.contentPath+mData.getUrl());
            });
            menuData.setChildren(children);
        });
        return MenuModel.buildMenuModel(parentMenu);
    }

    @Override
    public CheckResult<SysUser> loginUser(SysUser sysUser) {
        if(sysUser.getUsername().trim().isEmpty()){
            return CheckResult.createFailResult("账号为空");
        }else if(sysUser.getPassword().trim().isEmpty()){
            return CheckResult.createFailResult("密码为空");
        } else if(sysUser.getUsername().length()>30)
            return CheckResult.createFailResult("账号过长");
        else if(sysUser.getPassword().length()>30)
            return CheckResult.createFailResult("密码过长");
        SysUser dbUser=userDao.selectUserByUsername(sysUser.getUsername(),SysUser.USER);
        if(dbUser==null)
            return CheckResult.createFailResult("账号不存在!");
        if(!Md5Utils.md5(sysUser.getPassword()).equals(dbUser.getPassword())){
            return CheckResult.createFailResult("密码错误!");
        }
        userDao.updateUserLastLogin(HttpRequestUtil.getRemoteIp(),
                TimeUtils.getSimpleDateFormat().format(System.currentTimeMillis()),
                dbUser.getId());
        return CheckResult.createSuccessResult(dbUser,"登录成功");
    }

    @Override
    public SysUser getTestUser() {
        return userDao.selectUserByUsername("test",1);
    }

    @Override
    public Integer getUserBlogCount(SysUser user) {
        return userDao.selectUserBlogCountById(user.getId());
    }

    @Override
    public List<Article> getUserRecentBlogs(SysUser user) {
        return blogDao.selectUserBlogs(user.getId(),0,10);
    }

    @Override
    public CheckResult<Void> editUserInfo(SysUser user, SysUser uploadUser) {
        if(StringUtils.isEmptyString(uploadUser.getNickname())){
            return CheckResult.createFailResult("昵称为空!");
        }
        if(StringUtils.isEmptyString(uploadUser.getPassword())){
            return CheckResult.createFailResult("密码为空");
        }
        if(StringUtils.isEmptyString(uploadUser.getFace())){
            uploadUser.setFace(null);
        }else
            uploadUser.setFace(XssTool.encode(uploadUser.getFace().trim()));
        if(uploadUser.getSign()!=null&&uploadUser.getSign().trim().length()>50){
            return CheckResult.createFailResult("签名过长!");
        }
        if(uploadUser.getSign()!=null)
            uploadUser.setSign(XssTool.encode(uploadUser.getSign().trim()));
        uploadUser.setId(user.getId());
        uploadUser.setPassword(Md5Utils.md5(uploadUser.getPassword()));
        if(userDao.updateUserInfo(uploadUser)>0){
            commonDao.insertLog(user.getId(), SysOperation.OperationType.UPDATE_USER_INFO,
                    "修改个人信息",
                    TimeUtils.getSimpleDateFormat().format(System.currentTimeMillis()),
                    HttpRequestUtil.getRequest().getRemoteAddr(), "web");
            user.setSign(uploadUser.getSign());
            user.setFace(uploadUser.getFace());
            user.setNickname(uploadUser.getNickname());
            return CheckResult.createSuccessResult(null,"更新成功");
        }
        return CheckResult.createFailResult("更新失败");
    }

}
