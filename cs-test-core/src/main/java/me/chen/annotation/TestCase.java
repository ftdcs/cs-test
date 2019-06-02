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

    /**
     * 可以单独指定value来为name赋值 也可以直接用name来指定显示的名字
     * @return
     */
    String value();

    String name() default "";

    String desc() default "";

}
