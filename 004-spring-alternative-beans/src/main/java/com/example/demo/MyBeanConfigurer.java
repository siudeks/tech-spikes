package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanConfigurer {
    
    @Bean
    MyBean defaultBean(List<Optional<MyBean>> candidates) {
        return candidates.stream().flatMap(Optional::stream).findFirst().orElseThrow();
    }
}
