package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import akka.NotUsed;
import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.Behaviors;
import reactor.core.publisher.Mono;
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
    public void shouldRunSimpleBehavior() {
        
        var touched = new CompletableFuture<Boolean>()
            .completeOnTimeout(Boolean.FALSE, 100, TimeUnit.MILLISECONDS);

        var simpleMediator = Behaviors.setup(ctx -> {
            touched.complete(Boolean.TRUE);
            return Behaviors.stopped();
        });
        runner.spawn(simpleMediator, () -> { }, Duration.ofSeconds(1));

        Assertions.assertThat(touched.join()).isTrue();
    }

    @Test
    public void shouldRunSimpleSecondBehavior() {
        
        var touched = new CompletableFuture<Boolean>()
            .completeOnTimeout(Boolean.FALSE, 100, TimeUnit.MILLISECONDS);

        var firstMediator = Behaviors.stopped();
        var secondMediator = Behaviors.setup(ctx -> {
            touched.complete(Boolean.TRUE);
            return Behaviors.stopped();
        });

        runner.spawn(firstMediator, () -> { }, Duration.ofSeconds(1));
        runner.spawn(secondMediator, () -> { }, Duration.ofSeconds(1));

        Assertions.assertThat(touched.join()).isTrue();
    }

    @Test
    public void shouldTerminateSpawnedBehaviorAfterTimeout() {
        
        var actorKilled = MonoProcessor.<NotUsed>create();
        var mediator = Behaviors.setup(ctx -> {
            return Behaviors
                .receive(Object.class)
                .onSignal(PostStop.class, signal -> {
                    actorKilled.onNext(NotUsed.getInstance());
                    return Behaviors.stopped();} )
                .build();
        });
                    
        var terminationSignaled = MonoProcessor.<NotUsed>create();
        runner.spawn(mediator,
                     () -> terminationSignaled.onNext(NotUsed.getInstance()),
                     Duration.ofSeconds(0));

        assertThat(Mono
            .zip(actorKilled, terminationSignaled)
            .timeout(Duration.ofMillis(300))
            .blockOptional())
            .isNotEmpty();
    }

    @Test
    public void shouldNotNotifyWhenNotTimeouted() {
        var terminationSignaled = MonoProcessor.<Boolean>create();
        runner.spawn(Behaviors.stopped(),
                     () -> terminationSignaled.onNext(true),
                     Duration.ofMillis(300));

        assertThat(terminationSignaled
            .timeout(Duration.ofMillis(300), Mono.just(false))
            .blockOptional())
            .hasValue(false);
    }
}
