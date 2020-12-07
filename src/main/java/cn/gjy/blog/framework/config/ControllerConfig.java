package cn.gjy.blog.framework.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gujianyang
 * @date 2020/11/25
 */
public class ControllerConfig {

    private static List<Class<?>> controllerClasses=new ArrayList<>();

    static{
        register();
    }

    public static boolean USE_FILE_STATIC_REGISTER=false;//静态注册

    public static List<Class<?>> getControllerClasses() {
        return controllerClasses;
    }

    private static void register(){

    }
}
