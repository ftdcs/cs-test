package me.chen.core.resolver;

/**
 * @Author: ftdcs
 * @Date: 2019/05/19 0019 14:36
 * @Version 1.0
 */
public class LongResolver implements Resolver {

    @Override
    public Object resolver(Class type, String value) {
        return Long.parseLong(value);
    }
}
