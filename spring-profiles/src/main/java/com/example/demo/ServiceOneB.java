package com.example.demo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("b")
public class ServiceOneB implements ServiceOne {

    @Override
    public void printLine() {
        log.info("BBBBB");
    }
    
}
