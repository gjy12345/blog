package cn.gjy.framerwork.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//控制层路由
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Route {
    String value() default "";
    HttpMethod[] method() default {HttpMethod.GET};

    public static enum HttpMethod{
        GET,POST;
    }
}
