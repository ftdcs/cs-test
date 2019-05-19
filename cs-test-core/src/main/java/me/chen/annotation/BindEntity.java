package me.chen.annotation;

import java.lang.annotation.*;

/**
 * 将参数标识为是一个实体类
 * @Author: ftdcs
 * @Date: 2019/05/19 0019 1:38
 * @Version 1.0
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindEntity {

    /**
     * 当有多个同类型参数时需要通过value来指定别名
     * @return
     */
    String value() default "";
}
