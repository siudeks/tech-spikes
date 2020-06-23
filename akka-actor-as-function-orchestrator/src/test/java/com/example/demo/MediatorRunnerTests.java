package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import akka.NotUsed;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.Behaviors;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

public final class MediatorRunnerTests {

    private AsyncMediatorRunnerImpl runner = new AsyncMediatorRunnerImpl();

    @Test
    public void shouldSpawnSimpleBehavior() {
        
        var touched = new CompletableFuture<Boolean>()
            .completeOnTimeout(Boolean.FALSE, 100, TimeUnit.MILLISECONDS);

        var sut = Behaviors.setup(ctx -> {
            touched.complete(Boolean.TRUE);
            return Behaviors.stopped();
        });
        runner.spawn(sut, () -> { }, Duration.ofSeconds(1));

        Assertions.assertThat(touched.join()).isTrue();
    }

    @Test
    public void shouldTerminateSpawnedBehaviorAfterTimeout() {
        
        var postStopSignaled = MonoProcessor.<NotUsed>create();
        var sut = Behaviors.<NotUsed>setup(ctx -> {
            return Behaviors
                .receive(NotUsed.class)
                .onSignal(PostStop.class, signal -> {
                    postStopSignaled.onNext(NotUsed.getInstance());
                    return Behaviors.stopped();} )
                .build();
        });
                    
        var terminationSignaled = MonoProcessor.<NotUsed>create();
        runner.spawn(sut,
                     () -> terminationSignaled.onNext(NotUsed.getInstance()),
                     Duration.ofSeconds(0));

        assertThat(Mono
            .zip(postStopSignaled, terminationSignaled)
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
