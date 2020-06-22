package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import io.vavr.Function2;

@RestController
public class MyControllerOne {

    @Autowired
    private AsyncMediatorRunner runner;

    @GetMapping(path = "/")
    public CompletionStage<String> hello() {

        var result = new CompletableFuture<String>();

        // // var awaiter1 = new CompletableFuture<String>();
        // // var awaiter2 = new CompletableFuture<String>();

        // var step1 = Behaviors.setup(ctx -> {
        // return Behaviors.stopped();
        // });

        // runner.spawn(step1, () -> {
        // result.complete("aaaa");
        // }, Duration.ofSeconds(30));

        return result.thenApply(it -> it.toString());
    }

}

class MyMediator extends AbstractBehavior<MyMediator.Command> {

    interface Command {
    }

    private MyMediator(final ActorContext<MyMediator.Command> context) {
        super(context);
    }

    public static Behavior<Command> create(CompletableFuture<String> awaiter, CompletableFuture<String> result) {
        return Behaviors.setup(ctx -> new MyMediator(ctx));
    }

    @Override
    public Receive<Command> createReceive() {
        // TODO Auto-generated method stub
        return null;
    }

}

