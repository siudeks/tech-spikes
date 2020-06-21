package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import akka.actor.typed.ActorRef;

public class ActorWatcher {

    public static <T> ActorRef<T> of(ActorRef<T> watched, CompletableFuture<?> result, final Duration timeout) {
        return watched;
    }
}