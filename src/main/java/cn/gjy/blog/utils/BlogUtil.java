package cn.gjy.blog.utils;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class BlogUtil
 */
public class BlogUtil {

    public static String replaceUnsafeString(String s){
        s = s.replaceAll("<script", "&lt;script");
        s = s.replaceAll("<link","&lt;llink");
        return s;
    }
}
