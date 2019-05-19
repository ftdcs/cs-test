package me.chen.resolver;

import me.chen.core.resolver.Resolver;
import me.chen.model.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ftdcs
 * @Date: 2019/05/19 0019 2:28
 * @Version 1.0
 */
public class TestResolver implements Resolver{

    @Override
    public Object resolver(Class type, String value) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String name = type.getSimpleName();
        Test test = new Test();
        String test1 = request.getParameter("test1");
        String test2 = request.getParameter("test2");
        test.setTest1(test1);
        test.setTest2(test2);
        return test;
    }
}
