package me.chen.core.resolver;

/**
 * @Author: ftdcs
 * @Date: 2019/05/19 0019 13:59
 * @Version 1.0
 */
public class BooleanResolver implements Resolver {

    @Override
    public Object resolver(Class type, String value) {
        if(value.equalsIgnoreCase("true") || value.equals("1")){
            return true;
        }else{
            return false;
        }
    }
}
