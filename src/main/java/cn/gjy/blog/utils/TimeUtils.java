package cn.gjy.blog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static int getDays(String createTime) throws ParseException {
        if(createTime==null)
            return 0;
        Date date=getSimpleDateFormat().parse(createTime);
        long now=System.currentTimeMillis();
        long create=date.getTime();
        return (int) ((now-create)/(1000*60*60*24));
    }
}
