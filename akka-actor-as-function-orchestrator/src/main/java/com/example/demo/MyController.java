package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.scaladsl.AbstractBehavior;
import akka.actor.typed.scaladsl.Behaviors;
import io.vavr.Function2;

@RestController
public class MyController {

    @Autowired
    private AsyncMediatorRunner runner;

    @GetMapping(path = "/")
    public CompletionStage<String> hello() {

        var result = new CompletableFuture<String>();
        var awaiter = new CompletableFuture<String>();

        // var beh = Function2
        //     .of(MyMediator::create)
        //     .curried()
        //     .apply(awaiter);

        // runner.spawn(beh, new MyMediator.Command.Verify("some name", "some password"), Duration.ofSeconds(30));
        runner.spawn(Behaviors.empty(), () -> {}, Duration.ofSeconds(30));

        return result
            .thenApply(it -> it.toString());
    }

}

// class MyMediator extends AbstractBehavior<MyMediator.Command> {

//     interface Command {}

//     private MyMediator(ActorContext<Command> context) {
//         super(context);
//     }

//     public static Behavior<Command> create(CompletableFuture<String> awaiter, CompletableFuture<String> result) {
//         return 
//     }

// }
