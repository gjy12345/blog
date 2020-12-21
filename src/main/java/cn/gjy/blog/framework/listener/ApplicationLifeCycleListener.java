package cn.gjy.blog.framework.listener;

/**
 * @Author gujianyang
 * @Date 2020/12/21
 * @Class ApplicationDestroyListener
 * 监听程序关闭
 */
public interface ApplicationLifeCycleListener {

    //启动完成
    void onStart();

    //关闭
    void onDestroy();

}
