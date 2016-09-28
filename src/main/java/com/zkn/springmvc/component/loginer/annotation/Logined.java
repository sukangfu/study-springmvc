package com.zkn.springmvc.component.loginer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zkn.springmvc.component.loginer.enums.LoginResultType;

/**
 * 登录注解，使用此注解来确认是否需要登录之后才能操作
 * @author MR.SU
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logined {

	LoginResultType value() default LoginResultType.PAGE;
}
