package me.chen.core;

import me.chen.annotation.BindEntity;
import me.chen.annotation.BindValue;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ftdcs
 * @Date: 2019/05/16 0023:45:47
 * @Version 1.0
 */
public class RequestParamsHolder {

    private static final ThreadLocal<Map<String,Object>> dataObjMap = ThreadLocal.withInitial(() -> new HashMap<>());

    public static Map<String, Object> newDataObjMap(){
        Map<String, Object> stringObjectMap = dataObjMap.get();
        stringObjectMap.clear();
        return stringObjectMap;
    }
    public static Map<String, Object> getDataObjMap(){
        return dataObjMap.get();
    }

    public static Object[] resolveParams(Method method) throws ReflectiveOperationException {
        BindValue bindValue = method.getAnnotation(BindValue.class);
        if(bindValue!=null){
            newDataObjMap();
            String[] names = bindValue.names();
            Class[] types = bindValue.types();
            for (int i = 0; i < names.length; i++) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                String value = request.getParameter(names[i]);
                if(value != null && !value.equals("")){
                    ResolverFactory.resolveParams(names[i],value,types[i]);
                }

            }
        }
        Parameter[] parameters = method.getParameters();
        if(parameters!=null && parameters.length>0){
            Object[] objects = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                BindEntity bindEntity = parameters[i].getAnnotation(BindEntity.class);
                if(bindEntity!=null){
                    String name = parameters[i].getName();
                    Class<?> type = parameters[i].getType();
                    Object obj = ResolverFactory.resolveEntity(name,type,bindEntity);
                    objects[i] = obj;
                }else{
                    String name = parameters[i].getName();
                    Class<?> type = parameters[i].getType();
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    HttpServletRequest request = attributes.getRequest();
                    String value = request.getParameter(name);
                    Object obj = null;
                    if(value != null && !value.equals("")){
                        obj = ResolverFactory.resolveParams(name,value,type);
                    }
                    objects[i] = obj;
                }
            }
            return objects;
        }
        return null;
    }

}
