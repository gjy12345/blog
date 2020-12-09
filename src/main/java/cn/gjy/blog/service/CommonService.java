package cn.gjy.blog.service;

import cn.gjy.blog.model.SysOperation;
import cn.gjy.blog.model.SysUser;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class CommonService
 */
public interface CommonService {

    String getHourWelcome(Integer i);

    List<SysOperation> getUserOperations(SysUser user,Integer size);
}
