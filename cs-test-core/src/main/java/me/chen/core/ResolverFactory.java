package me.chen.core;

import lombok.extern.slf4j.Slf4j;
import me.chen.annotation.BindEntity;
import me.chen.core.RequestParamsHolder;
import me.chen.core.resolver.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author: ftdcs
 * @Date: 2019/05/17 0000:00:05
 * @Version 1.0
 */
@Slf4j
public class ResolverFactory {

    private static final String INT_SIMPLE_NAME = "int";
    private static final String INT_ARRAY_SIMPLE_NAME = "int[]";
    private static final String INTEGER_SIMPLE_NAME = "Integer";
    private static final String INTEGER_ARRAY_SIMPLE_NAME = "Integer[]";
    private static final String STRING_SIMPLE_NAME = "String";
    private static final String STRING_ARRAY_SIMPLE_NAME = "String[]";
    private static final String BASIC_BOOLEAN_SIMPLE_NAME = "boolean";
    private static final String BASIC_BOOLEAN_ARRAY_SIMPLE_NAME = "boolean[]";
    private static final String BOOLEAN_SIMPLE_NAME = "Boolean";
    private static final String BOOLEAN_ARRAY_SIMPLE_NAME = "Boolean[]";
    private static final String CHAR_SIMPLE_NAME = "char";
    private static final String CHAR_ARRAY_SIMPLE_NAME = "char[]";
    private static final String CHARACTER_SIMPLE_NAME = "Character";
    private static final String CHARACTER_ARRAY_SIMPLE_NAME = "Character[]";
    private static final String BASIC_SHORT_SIMPLE_NAME = "short";
    private static final String BASIC_SHORT_ARRAY_SIMPLE_NAME = "short[]";
    private static final String SHORT_SIMPLE_NAME = "Short";
    private static final String SHORT_ARRAY_SIMPLE_NAME = "Short[]";
    private static final String BASIC_LONG_SIMPLE_NAME = "long";
    private static final String BASIC_LONG_ARRAY_SIMPLE_NAME = "long[]";
    private static final String LONG_SIMPLE_NAME = "Long";
    private static final String LONG_ARRAY_SIMPLE_NAME = "Long[]";
    private static final String BASIC_DOUBLE_SIMPLE_NAME = "double";
    private static final String BASIC_DOUBLE_ARRAY_SIMPLE_NAME = "double[]";
    private static final String DOUBLE_SIMPLE_NAME = "Double";
    private static final String DOUBLE_ARRAY_SIMPLE_NAME = "Double[]";
    private static final String BASIC_BYTE_SIMPLE_NAME = "byte";
    private static final String BASIC_BYTE_ARRAY_SIMPLE_NAME = "byte[]";
    private static final String BYTE_SIMPLE_NAME = "Byte";
    private static final String BYTE_ARRAY_SIMPLE_NAME = "Byte[]";
    private static final String DATE_SIMPLE_NAME = "Date";

    //不考虑线程安全问题
    private static Map<String, Resolver> resolverMap;

    static {
        resolverMap = new HashMap<>();
        resolverMap.put(INT_SIMPLE_NAME, new IntResolver());
        resolverMap.put(INT_ARRAY_SIMPLE_NAME, new IntArrayResolver());

        resolverMap.put(INTEGER_SIMPLE_NAME, new IntResolver());
        resolverMap.put(INTEGER_ARRAY_SIMPLE_NAME, new IntArrayResolver());

        resolverMap.put(STRING_SIMPLE_NAME, new StringResolver());
        resolverMap.put(STRING_ARRAY_SIMPLE_NAME, new StringArrayResolver());

        resolverMap.put(BASIC_BOOLEAN_SIMPLE_NAME, new BooleanResolver());
        resolverMap.put(BASIC_BOOLEAN_ARRAY_SIMPLE_NAME, new BooleanArrayResolver());

        resolverMap.put(BOOLEAN_SIMPLE_NAME, new BooleanResolver());
        resolverMap.put(BOOLEAN_ARRAY_SIMPLE_NAME, new BooleanArrayResolver());

        resolverMap.put(CHAR_SIMPLE_NAME, new CharResolver());
        resolverMap.put(CHAR_ARRAY_SIMPLE_NAME, new CharArrayResolver());

        resolverMap.put(CHARACTER_SIMPLE_NAME, new CharResolver());
        resolverMap.put(CHARACTER_ARRAY_SIMPLE_NAME, new CharArrayResolver());

        resolverMap.put(BASIC_SHORT_SIMPLE_NAME, new ShortResolver());
        resolverMap.put(BASIC_SHORT_ARRAY_SIMPLE_NAME, new ShortArrayResolver());

        resolverMap.put(SHORT_SIMPLE_NAME, new ShortResolver());
        resolverMap.put(SHORT_ARRAY_SIMPLE_NAME, new ShortArrayResolver());

        resolverMap.put(BASIC_LONG_SIMPLE_NAME, new LongResolver());
        resolverMap.put(BASIC_LONG_ARRAY_SIMPLE_NAME, new LongArrayResolver());

        resolverMap.put(LONG_SIMPLE_NAME, new LongResolver());
        resolverMap.put(LONG_ARRAY_SIMPLE_NAME, new LongArrayResolver());

        resolverMap.put(BASIC_DOUBLE_SIMPLE_NAME, new DoubleResolver());
        resolverMap.put(BASIC_DOUBLE_ARRAY_SIMPLE_NAME, new DoubleArrayResolver());

        resolverMap.put(DOUBLE_SIMPLE_NAME, new DoubleResolver());
        resolverMap.put(DOUBLE_ARRAY_SIMPLE_NAME, new DoubleArrayResolver());

        resolverMap.put(BASIC_BYTE_SIMPLE_NAME, new ByteResolver());
        resolverMap.put(BASIC_BYTE_ARRAY_SIMPLE_NAME, new ByteArrayResolver());

        resolverMap.put(BYTE_SIMPLE_NAME, new ByteResolver());
        resolverMap.put(BYTE_ARRAY_SIMPLE_NAME, new ByteArrayResolver());

        resolverMap.put(DATE_SIMPLE_NAME,new DateResolver());
    }

    public static void registerResolver(Class type, Resolver resolver) {
        resolverMap.put(type.getSimpleName(), resolver);
    }

    public static Object resolveParams(String name, String value, Class type) {
        Resolver resolver = null;
        Objects.requireNonNull(type, "参数类型不能为空...");
        String simpleName = type.getSimpleName();
        resolver = resolverMap.get(simpleName);
        Objects.requireNonNull(resolver, simpleName + "类型无法解析...");
        Object val = null;
        try {
            val = resolver.resolver(type, value);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        RequestParamsHolder.getDataObjMap().put(name, val);
        return val;
    }

    public static Object resolveEntity(String name, Class type,BindEntity bindEntity) throws IllegalAccessException, InstantiationException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (resolverMap.containsKey(type.getSimpleName())) {
            return resolveParams(name, null, type);
        } else {
            Object entity = type.newInstance();
            Field[] declaredFields = type.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String fieldName = declaredField.getName();
                String simpleName = Optional.ofNullable(bindEntity.value()).filter(s -> !s.equals("")).orElse(type.getSimpleName());
                String key = simpleName + "." + fieldName;
                String value = request.getParameter(key);
                Class fieldType = declaredField.getType();
                Object fieldVal = resolveParams(key, value, fieldType);
                fieldName = upperFirst(fieldName);
                fieldName = "set" + fieldName;
                try {
                    Method method = type.getMethod(fieldName,fieldType);
                    method.invoke(entity,fieldVal);
                } catch (NoSuchMethodException e) {
                    log.error(e.getMessage(),e);
                } catch (InvocationTargetException e) {
                    log.error(e.getMessage(),e);
                }
            }
            return entity;
        }
    }


    private static String upperFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
