package com.immuserisk.annotation;

import java.lang.annotation.*;

/**
 * 登录用户信息注解
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {
    /**
     * 是否查询用户详细信息
     */
    boolean withInfo() default false;
}
