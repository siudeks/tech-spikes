package com.example.springevents;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.info(event.toString());
    }
    
}
