package me.chen.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: ftdcs
 * @Date: 2019/05/16 0020:43:39
 * @Version 1.0
 */
@Component
@Slf4j
public class WebListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        ResolverFactory.registerResolver(Test.class,new TestResolver());
    }
}
