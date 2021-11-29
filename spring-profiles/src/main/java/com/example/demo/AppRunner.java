package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    ServiceOne serviceOne;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        printLine();
        serviceOne.printLine();
        printLine();
    }

    private void printLine() {
        System.out.println("--------------------");
    }
    
}
