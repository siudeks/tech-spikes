package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.Signal;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.Behaviors;
import lombok.SneakyThrows;
import reactor.core.publisher.MonoProcessor;

public final class MediatorRunnerTests {

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
        MonoProcessor<Signal> cont = MonoProcessor.<Signal>create();
        var mediator = Behaviors.<Object>setup(ctx -> {
            return Behaviors.receiveSignal((ctx_, sig) -> {
                cont.onNext(sig);
                return Behaviors.empty();
            });
        });
        var ignoredMessage = new Object();
        runner.spawn(mediator, ignoredMessage, Duration.ZERO);

        assertThat(cont
            .timeout(Duration.ofMillis(300))
            .blockOptional())
            .isNotEmpty();
            
    }
}
