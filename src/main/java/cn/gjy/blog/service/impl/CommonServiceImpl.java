package cn.gjy.blog.service.impl;

import cn.gjy.blog.dao.CommonDao;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.annotation.Service;
import cn.gjy.blog.model.SysOperation;
import cn.gjy.blog.model.SysUser;
import cn.gjy.blog.service.CommonService;

import java.util.Calendar;
import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class CommonServiceImpl
 */
@Service(CommonService.class)
public class CommonServiceImpl implements CommonService{

    @InitObject
    private CommonDao commonDao;

    //获取欢迎词
    @Override
    public String getHourWelcome(Integer hour) {
        if(hour==null)
            hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if(hour<=5){
            return "凌晨好";
        }else if(hour<=10){
            return "早上好";
        }else if(hour<=13){
            return "中午好";
        }else if(hour<=18){
            return "下午好";
        }else if(hour<=24){
            return "晚上好";
        }
        throw new RuntimeException("错误的时间:"+hour);
    }

    //获取操作日志
    @Override
    public List<SysOperation> getUserOperations(SysUser user,Integer size) {
        if(size==null)
            size=10;
        return commonDao.getUserOperation(user.getId(),size);
    }
}
