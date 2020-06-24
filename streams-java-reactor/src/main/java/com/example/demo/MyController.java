package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class MyController {

    @Autowired
    @GetMapping(path = "/")
    public Mono<String> hello() {

        return Mono.just("Hello");
    }
}