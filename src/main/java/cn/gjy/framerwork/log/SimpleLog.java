package cn.gjy.framerwork.log;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
public final class SimpleLog {

    private Class<?> c;
    private String className;
    private DateFormat dateFormat=DateFormat.getDateTimeInstance();

    private SimpleLog(Class<?> c){
        this.c=c;
        this.className=c.getName();
    }

    public static SimpleLog log(Class<?> c){
        return new SimpleLog(c);
    }

    public void v(String s){
        System.out.println("["+dateFormat.format(new Date())+"]  "+className+":  "+s);
    }

    public void d(String s){
        v(s);
    }

    public void i(String s){
        v(s);
    }

    public void e(String s) {
        System.err.println("["+dateFormat.format(new Date())+"]  "+className+":  "+s);
    }
}
