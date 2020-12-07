package cn.gjy.blog.framework.model;

import java.lang.reflect.Method;

/**
 * @author gujianyang
 * @date 2020/11/25
 */
public class MatchResult {

    private Object o;
    private Method method;

    public MatchResult() {
    }

    public MatchResult(Object o, Method method) {
        this.o = o;
        this.method = method;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
