package com.example.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MyBeanConfigurerDefault {
    
    @Bean
    @Primary
    @ConditionalOnProperty(name = "my-variable", havingValue = "true", matchIfMissing = true )
    MyBean myDefaultBean() {
        MyBean.testCounter++;
        return new MyBean("my default bean");
    }
}
