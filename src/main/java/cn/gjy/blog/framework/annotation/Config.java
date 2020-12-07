package cn.gjy.blog.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//配置
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Config {
    Class<?> value();

    int level() default 0;
}
