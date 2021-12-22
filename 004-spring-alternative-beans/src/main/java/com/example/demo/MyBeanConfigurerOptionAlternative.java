package com.example.demo;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class MyBeanConfigurerOptionAlternative {
    
    @Bean
    @Order(20)
    Optional<MyBean> myAlternativeBean() {
        return Optional.of(new MyBean("my alternative bean"));
    }
}
