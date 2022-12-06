package com.zyxx.common.annotation;

import java.lang.annotation.*;

/**
 * 系统操作日志的注解
 *
 * @author zxy
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 模块名
     *
     * @return
     */
    String model() default "";
}