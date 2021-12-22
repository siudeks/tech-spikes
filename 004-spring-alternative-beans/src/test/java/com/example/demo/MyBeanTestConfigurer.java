package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanTestConfigurer {
    
    @Bean
    MyBean myTestBean() {
        return new MyBean("my test bean");
    }
}
