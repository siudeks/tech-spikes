package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import akka.actor.typed.scaladsl.Behaviors;

@RestController
public class MyControllerA {

    @Autowired
    private BehSupport runner;

    @GetMapping(path = "/a")
    public CompletionStage<String> hello() {

        var result = new CompletableFuture<String>();
        runner.spawn(Behaviors.empty(), () -> {
            result.complete("empty!");
        }, Duration.ofSeconds(5));

        return result
            .thenApply(it -> it.toString());
    }
}