package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanConfigurerFallback {
    
    @Bean
    MyBean myFallbackBean() {
        MyBean.testCounter++;
        return new MyBean("my fallback bean");
    }
}
