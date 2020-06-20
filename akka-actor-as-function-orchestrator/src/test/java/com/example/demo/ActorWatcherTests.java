package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.typed.javadsl.Behaviors;

public class ActorWatcherTests {
    
    static final ActorTestKit testKit = ActorTestKit.create();

    @AfterAll
    public static void cleanup() {
        testKit.shutdownTestKit();
    }

    @Test
    public void shouldKillWatchedActorAndSetResultWhenTimeouted() {
        var result = new CompletableFuture<>();
        var sut = testKit.spawn(Behaviors.empty());
        ActorWatcher.of(sut, result, Duration.ZERO);
    }

    @Test
    public void shouldTryDefaultResultWhenWatchedActorTerminated() {

    }
}