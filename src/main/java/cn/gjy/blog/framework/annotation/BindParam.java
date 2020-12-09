package cn.gjy.blog.framework.annotation;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//index 从0开始
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
public @interface BindParam {
    String value() default "";
    Class from() default HttpServletRequest.class;
    String header() default "";
}
