package cn.gjy.framerwork.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//持久层
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Dao {
}
