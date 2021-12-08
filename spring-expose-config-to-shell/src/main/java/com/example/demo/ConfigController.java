package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;

@RestController
public class ConfigController {

    @GetMapping(path = "config", consumes = {})
    MyConfig config() {
        return new MyConfig("value1", "value2");
    }
}

@Value
class MyConfig {
    private String variable1;
    private String variable2;
}
