package me.chen.core.resolver;

import java.util.regex.Matcher;

/**
 * @Author: ftdcs
 * @Date: 2019/05/19 0019 14:42
 * @Version 1.0
 */
public class DoubleArrayResolver implements Resolver {
    
    @Override
    public Object resolver(Class type, String value) {
        Matcher matcher = ARRAY_PATTERN.matcher(value);
        if(matcher.matches()){
            String s = matcher.group();
            String[] datas = s.split(",");
            Double[] args = new Double[datas.length];
            for (int i = 0; i < datas.length; i++) {
                args[i] = Double.parseDouble(datas[i]);
            }
            return args;
        }else{
            if(value.contains(",")){
                String[] datas = value.split(",");
                Double[] args = new Double[datas.length];
                for (int i = 0; i < datas.length; i++) {
                    args[i] = Double.parseDouble(datas[i]);
                }
                return args;
            }else{
                return new Double[]{Double.parseDouble(value)};
            }
        }
    }
}
