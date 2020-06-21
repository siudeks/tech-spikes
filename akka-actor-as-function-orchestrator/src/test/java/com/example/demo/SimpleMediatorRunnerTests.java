package com.example.demo;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.typesafe.config.ConfigFactory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.typed.javadsl.Behaviors;
import lombok.SneakyThrows;

public final class SimpleMediatorRunnerTests {

    private ActorTestKit actorTestKit;
    private SimpleMediatorRunnerImpl runner;

    @BeforeEach
    void setUp() {
        runner = new SimpleMediatorRunnerImpl();
        actorTestKit = ActorTestKit.create(runner.actorSystem);
    }

    @AfterEach
    public void done() {
        actorTestKit.shutdownTestKit();
    }

    @Test
    @SneakyThrows
    public void shouldRunSimpleBehavior() {
        
        var touched = new CompletableFuture<Boolean>()
            .completeOnTimeout(Boolean.FALSE, 100, TimeUnit.MILLISECONDS);

        var simpleMediator = Behaviors.receiveMessage((Object ignored) -> {
            touched.complete(Boolean.TRUE);
            return Behaviors.stopped();
        });
        var simpleMediatorInitialMessage = new Object();

        runner.spawn(simpleMediator, simpleMediatorInitialMessage, Duration.ofSeconds(1));

        Assertions.assertThat(touched.get()).isTrue();
    }

    @Test
    @SneakyThrows
    public void shouldRunSimpleSecondBehavior() {
        
        var touched = new CompletableFuture<Boolean>()
            .completeOnTimeout(Boolean.FALSE, 100, TimeUnit.MILLISECONDS);

        var firstMediator = Behaviors.stopped();
        var secondMediator = Behaviors.receiveMessage((Object ignored) -> {
            touched.complete(Boolean.TRUE);
            return Behaviors.stopped();
        });

        var ignoredMessage = new Object();
        runner.spawn(firstMediator, ignoredMessage, Duration.ofSeconds(1));
        runner.spawn(secondMediator, ignoredMessage, Duration.ofSeconds(1));

        Assertions.assertThat(touched.get()).isTrue();
    }

    @Test
    public void shouldTerminateSpawnedBehaviorAfterTimeout() {
        var mediator = Behaviors.stopped();
        var ignoredMessage = new Object();
        runner.spawn(mediator, ignoredMessage, Duration.ZERO);

        Assertions.assertThat(actorTestKit);
    }
}