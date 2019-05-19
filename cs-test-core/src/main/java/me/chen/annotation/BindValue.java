package me.chen.annotation;


import me.chen.core.RequestParamsHolder;

import java.lang.annotation.*;

/**
 * 给方法绑定额外的参数
 * 通过{@link RequestParamsHolder#getDataObjMap()} 来获取所有你绑定的参数
 * 只支持基本数据类型
 * @Author: ftdcs
 * @Date: 2019/05/16 0023:11:47
 * @Version 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindValue {

    String[] names();
    
    Class[] types();
}
