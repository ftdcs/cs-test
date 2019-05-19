package me.chen.core.resolver;

import java.util.regex.Matcher;

/**
 * @Author: ftdcs
 * @Date: 2019/05/19 0019 14:06
 * @Version 1.0
 */
public class BooleanArrayResolver implements Resolver {

    @Override
    public Object resolver(Class type, String value) {
        Matcher matcher = ARRAY_PATTERN.matcher(value);
        if(matcher.matches()){
            String s = matcher.group();
            String[] datas = s.split(",");
            boolean[] args = new boolean[datas.length];
            for (int i = 0; i < datas.length; i++) {
                args[i] = parseBoolean(datas[i]);
            }
            return args;
        }else{
            if(value.contains(",")){
                String[] datas = value.split(",");
                boolean[] args = new boolean[datas.length];
                for (int i = 0; i < datas.length; i++) {
                    args[i] = parseBoolean(datas[i]);
                }
                return args;
            }else{

                return new boolean[]{parseBoolean(value)};
            }
        }
    }


    private Boolean parseBoolean(String value){
        if(value.equalsIgnoreCase("true") || value.equals("1")){
            return true;
        }else{
            return false;
        }
    }
}
