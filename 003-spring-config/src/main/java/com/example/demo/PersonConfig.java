package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("person")
@Data
class PersonConfig {

    private String name;

    public void setName(String value) {
        name = value;
    }

    public String getName() {
        return name;
    }

    private String surname;
}
