package me.chen.core;

/**
 * @Author: ftdcs
 * @Date: 2019/05/16 0016:52:37
 * @Version 1.0
 */
public class StringBuilderHolder {

    private static final ThreadLocal<StringBuilder> stringBuilderHolder = ThreadLocal.withInitial(() -> new StringBuilder());

    public static StringBuilder newStringBuilder(){
        StringBuilder stringBuilder = stringBuilderHolder.get();
        stringBuilder.setLength(0);
        return stringBuilder;
    }

    public static StringBuilder getStringBuilder(){
        return stringBuilderHolder.get();
    }


}
