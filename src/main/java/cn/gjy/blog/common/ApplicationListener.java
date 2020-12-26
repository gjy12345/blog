package cn.gjy.blog.common;

import cn.gjy.blog.framework.annotation.Component;
import cn.gjy.blog.framework.annotation.InitObject;
import cn.gjy.blog.framework.listener.ApplicationLifeCycleListener;
import cn.gjy.blog.framework.log.SimpleLog;
import cn.gjy.blog.system.SystemInfoRunnable;

/**
 * @Author gujianyang
 * @Date 2020/12/21
 * @Class ApplicationListener
 */
@Component
public class ApplicationListener implements ApplicationLifeCycleListener {

    private SimpleLog log=SimpleLog.log(ApplicationListener.class);

    @InitObject
    private SystemInfoRunnable systemInfoRunnable;


    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        systemInfoRunnable.stopWork();
    }
}
