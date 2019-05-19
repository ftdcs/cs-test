package me.chen.listener;

import lombok.extern.slf4j.Slf4j;
import me.chen.core.Reflex;
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
public class MyWebListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Reflex reflex = contextRefreshedEvent.getApplicationContext().getBean(Reflex.class);
        log.info("scan class start...");
        long startTime = System.currentTimeMillis();
        reflex.scanClass();
        long endTime = System.currentTimeMillis();
        log.info("scan class end...耗时 {} ms",(endTime-startTime));
    }
}
