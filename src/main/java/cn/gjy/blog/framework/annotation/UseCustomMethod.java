package cn.gjy.blog.framework.annotation;

import cn.gjy.blog.framework.Invocation.CusMethodSql;
import cn.gjy.blog.framework.Invocation.DaoInvocationHandlerImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author gujianyang
 * @Date 2020/12/11
 * @Class UseCustomMethod
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface UseCustomMethod {
    
    Class<? extends CusMethodSql> value();

    DaoInvocationHandlerImpl.SqlType sqlType();
}
