package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonBasedService {
   
    @Autowired
    PersonConfig personConfig;

    public String getName() {
        return String.format("%s %s", personConfig.getName(), personConfig.getSurname());
    }
}
