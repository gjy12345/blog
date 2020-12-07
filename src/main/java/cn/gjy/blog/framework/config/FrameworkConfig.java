package cn.gjy.blog.framework.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author gujianyang
 * @date 2020/11/28
 */
public class FrameworkConfig {

    static {
        try {
            File file=new File(FrameworkConfig.class.getResource("/config.properties").getFile());
            Properties properties=new Properties();
            properties.load(new FileInputStream(file));
            basePackage= (String) properties.getOrDefault("model.basePackage","");
            xssFilter=Boolean.parseBoolean((String) properties.getOrDefault("xss","false"));
            viewRoot= (String) properties.getOrDefault("view.path.root","");
            viewEnd= (String) properties.getOrDefault("view.path.end","");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String basePackage;
    public static boolean xssFilter;
    public static String viewRoot;
    public static String viewEnd;
}
