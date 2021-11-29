package com.example.demo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("a")
public class ServiceOneA implements ServiceOne {

    @Override
    public void printLine() {
        log.info("AAAAA");
    }
    
}
