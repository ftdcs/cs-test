package me.chen.core.resolver;

/**
 * @Author: ftdcs
 * @Date: 2019/05/19 0019 1:13
 * @Version 1.0
 */
public class IntResolver implements Resolver {

    @Override
    public Object resolver(Class type, String value) {
        return Integer.parseInt(value);
    }
}
