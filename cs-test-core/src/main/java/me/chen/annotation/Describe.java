package me.chen.annotation;

import java.lang.annotation.*;

/**
 * 添加对方法或字段的描述信息
 * @Author: ftdcs
 * @Date: 2019/05/16 0019:00:23
 * @Version 1.0
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Describe {

    String desc() default "未设置";
}
