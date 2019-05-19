package me.chen.core.resolver;

import java.util.regex.Matcher;

/**
 * @Author: ftdcs
 * @Date: 2019/05/19 0019 14:37
 * @Version 1.0
 */
public class LongArrayResolver implements Resolver {

    @Override
    public Object resolver(Class type, String value) {
        Matcher matcher = ARRAY_PATTERN.matcher(value);
        if(matcher.matches()){
            String s = matcher.group();
            String[] datas = s.split(",");
            long[] args = new long[datas.length];
            for (int i = 0; i < datas.length; i++) {
                args[i] = Long.parseLong(datas[i]);
            }
            return args;
        }else{
            if(value.contains(",")){
                String[] datas = value.split(",");
                long[] args = new long[datas.length];
                for (int i = 0; i < datas.length; i++) {
                    args[i] = Long.parseLong(datas[i]);
                }
                return args;
            }else{
                return new long[]{Long.parseLong(value)};
            }
        }
    }
}
