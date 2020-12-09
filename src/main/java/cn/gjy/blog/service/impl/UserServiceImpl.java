package cn.gjy.blog.service.impl;

import cn.gjy.blog.dao.UserDao;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Service;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.model.CheckResult;
import cn.gjy.blog.model.MenuModel;
import cn.gjy.blog.model.SysUser;
import cn.gjy.blog.service.UserService;
import cn.gjy.blog.utils.Md5Utils;

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
        return CheckResult.createSuccessResult(dbUser,"登录成功");
    }

    @Override
    public SysUser getTestUser() {
        return userDao.selectUserByUsername("test",1);
    }

    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
