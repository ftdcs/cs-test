package me.chen.annotation;

import java.lang.annotation.*;

/**
 * 注册一个TestCase
 * @Author: ftdcs
 * @Date: 2019/05/16 0015:54:41
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestCase {

    String name();

    String desc();

}
