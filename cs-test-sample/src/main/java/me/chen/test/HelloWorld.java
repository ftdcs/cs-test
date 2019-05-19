package me.chen.test;

import me.chen.annotation.BindEntity;
import me.chen.annotation.BindValue;
import me.chen.annotation.Describe;
import me.chen.annotation.TestCase;
import me.chen.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: ftdcs
 * @Date: 2019/05/17 0017 15:47
 * @Version 1.0
 */
@Component
@TestCase(name="hello world",desc = "hello world sample for cs-test-case")
public class HelloWorld {

    @Describe(desc = "输出Hello World")
//    @BindValue(names = {"keyword"},types = {String.class})
    public String helloworld(String keyword){
        String result = Optional.ofNullable(keyword).orElse("Hello World");
        return result;
    }

    public String helloworld(@BindEntity Test test,@BindEntity("test2") Test test2){
        return test.toString();
    }
}
