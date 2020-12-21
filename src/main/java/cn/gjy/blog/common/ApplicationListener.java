package cn.gjy.blog.common;

import cn.gjy.blog.framework.annotation.Component;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.listener.ApplicationLifeCycleListener;
import cn.gjy.blog.framework.log.SimpleLog;

/**
 * @Author gujianyang
 * @Date 2020/12/21
 * @Class ApplicationListener
 */
@Component
public class ApplicationListener implements ApplicationLifeCycleListener {

    private SimpleLog log=SimpleLog.log(ApplicationListener.class);


    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
