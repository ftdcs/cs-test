package me.chen.core.resolver;

import java.util.regex.Matcher;

/**
 * @Author: ftdcs
 * @Date: 2019/05/17 0000:10:25
 * @Version 1.0
 */
public class IntArrayResolver implements Resolver {

    @Override
    public Object resolver(Class type, String value) {
        Matcher matcher = ARRAY_PATTERN.matcher(value);
        if(matcher.matches()){
            String s = matcher.group();
            String[] datas = s.split(",");
            int[] args = new int[datas.length];
            for (int i = 0; i < datas.length; i++) {
                args[i] = Integer.parseInt(datas[i]);
            }
            return args;
        }else{
            if(value.contains(",")){
                String[] datas = value.split(",");
                int[] args = new int[datas.length];
                for (int i = 0; i < datas.length; i++) {
                    args[i] = Integer.parseInt(datas[i]);
                }
                return args;
            }else{
                return new int[]{Integer.parseInt(value)};
            }
        }
    }
}
