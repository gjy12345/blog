package cn.gjy.blog.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//驼峰风格 自动映射将字段的驼峰转换成小写或者大写
//如: userName-->user_name
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface UseHump {

    Change value() default Change.HUMP_TO_LOWER;
    String separator() default "_"; //间隔符
    public enum Change{
        HUMP_TO_LOWER,HUMP_TO_UPPER;
    }
}
