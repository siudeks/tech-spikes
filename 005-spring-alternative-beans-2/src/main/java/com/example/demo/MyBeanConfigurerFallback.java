package com.example.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanConfigurerFallback {
    
    @Bean
    @ConditionalOnMissingBean
    MyBean myFallbackBean() {
        MyBean.testCounter++;
        return new MyBean("my fallback bean");
    }
}
