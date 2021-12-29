package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AppLogicRunner implements ApplicationRunner {

    @Autowired
    PersonBasedService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Hello");
    }
    
}
