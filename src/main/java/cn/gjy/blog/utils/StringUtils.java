package cn.gjy.blog.utils;

/**
 * @Author gujianyang
 * @Date 2020/12/11
 * @Class StringUtils
 */
public class StringUtils {

    public static boolean isEmptyString(String s){
        return s==null||s.trim().isEmpty();
    }
}
