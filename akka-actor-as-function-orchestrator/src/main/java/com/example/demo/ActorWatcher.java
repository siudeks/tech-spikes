package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import akka.NotUsed;
import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.scaladsl.Behaviors;

public class ActorWatcher {

    public static void of(ActorRef<?> watched, CompletableFuture<?> result, final Duration timeout,) {
    }
}