package cn.gjy.blog.framework.log;

import java.io.PrintStream;
import java.io.PrintWriter;
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

    private static final PrintStream out,err;

    static {
        out=System.out;
        err=System.err;
    }

    private SimpleLog(Class<?> c){
        this.c=c;
        this.className=c.getName();
    }

    public static SimpleLog log(Class<?> c){
        return new SimpleLog(c);
    }

    public void v(String s){
        out.println("[Verbose] ["+dateFormat.format(new Date())+"]  "+className+":  "+s);
    }

    public void d(String s){
        out.println("[Debug] ["+dateFormat.format(new Date())+"]  "+className+":  "+s);
    }

    public void i(String s){
        out.println("[Info] ["+dateFormat.format(new Date())+"]  "+className+":  "+s);
    }

    public void e(String s) {
        err.println("[Error] ["+dateFormat.format(new Date())+"]  "+className+":  "+s);
    }
}
