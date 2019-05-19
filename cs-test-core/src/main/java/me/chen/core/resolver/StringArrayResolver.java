package me.chen.core.resolver;

import java.util.regex.Matcher;

/**
 * @Author: ftdcs
 * @Date: 2019/05/19 0019 0:53
 * @Version 1.0
 */
public class StringArrayResolver implements Resolver {

    @Override
    public Object resolver(Class type, String value) {
        Matcher matcher = ARRAY_PATTERN.matcher(value);
        if(matcher.matches()){
            String s = matcher.group();
            String[] args = s.split(",");
            return args;
        }else{
            if(value.contains(",")){
                String[] args = value.split(",");
                return args;
            }else{
                return new String[]{value};
            }
        }
    }
}
