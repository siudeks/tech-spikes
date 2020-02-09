package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import akka.actor.ActorSystem;
import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.BehaviorTestKit;
import akka.testkit.javadsl.TestKit;
import lombok.SneakyThrows;

public final class MyMediatorTests {

    static final ActorTestKit testKit = ActorTestKit.create();

    @AfterAll
    public static void cleanup() {
        testKit.shutdownTestKit();
    }

    @Test
    @SneakyThrows
    public void shouldTimeoutAndStop() {

        final var result = new CompletableFuture<MyMediator.Result>();

        final VerificationService.Do timeoutedService = (claim) -> new CompletableFuture<>();
        final var beh = MyMediator.create(result, Duration.ZERO, timeoutedService);

        // final var test = BehaviorTestKit.create(beh);
        var sut = testKit.spawn(beh);
        sut.tell(new MyMediator.Command.Verify("ignored", "ignored"));

        Assertions
            .assertThat(result.get())
            .isExactlyInstanceOf(MyMediator.Result.InternalTimeout.class);

    }
}