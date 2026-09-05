package com.member.system.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {

    /** 模块 */
    String module() default "";

    /** 操作说明 */
    String value() default "";

    /** 是否记录请求参数 */
    boolean recordParams() default true;
}
