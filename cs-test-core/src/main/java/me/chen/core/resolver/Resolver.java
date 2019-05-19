package me.chen.core.resolver;


import java.util.regex.Pattern;

/**
 * @Author: ftdcs
 * @Date: 2019/05/17 0000:04:44
 * @Version 1.0
 */
public interface Resolver {

    String ARRAY_REGEX = "[.*]";
    Pattern ARRAY_PATTERN = Pattern.compile(ARRAY_REGEX);

    Object resolver(Class type, String value) throws Exception;
}
