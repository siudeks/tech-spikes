package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    private SimpleActorFactory actorFactory;

    @GetMapping(path = "/")
    public CompletionStage<String> hello() {

        var result = new CompletableFuture<MyMediator.Result>();

        var nonRespondingService = (VerificationService.Do) (claim) -> new CompletableFuture<>();
        var beh = MyMediator
            .create(result, Duration.ofSeconds(5), nonRespondingService);

        actorFactory.spawn(beh);

        return result
            .thenApply(it -> it.toString());
             
    }
}