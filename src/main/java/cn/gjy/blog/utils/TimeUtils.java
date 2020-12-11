package cn.gjy.blog.utils;

import java.text.SimpleDateFormat;

/**
 * @Author gujianyang
 * @Date 2020/12/12
 * @Class TimeUtils
 */
public class TimeUtils {
    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal=new ThreadLocal<>();
    public static SimpleDateFormat getSimpleDateFormat(){
        if(simpleDateFormatThreadLocal.get()==null){
            simpleDateFormatThreadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }
        return simpleDateFormatThreadLocal.get();
    }
}
