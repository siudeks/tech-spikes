package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class MyBeanConfigurerFallback {
    
    @Bean
    @Order(20)
    Optional<MyBean> myFallbackBean(List<Optional<MyBean>> beans) {
        if (!beans.isEmpty()) return Optional.empty();

        return Optional.of(new MyBean("my fallback bean"));
    }
}
