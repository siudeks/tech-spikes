package com.example.demo;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class MyBeanConfigurerOptionDefault {
    
    @Bean
    @ConditionalOnProperty(name = "my-variable")
    @Order(10)
    Optional<MyBean> myDefaultBean() {
        return Optional.of(new MyBean("my default bean"));
    }
}
