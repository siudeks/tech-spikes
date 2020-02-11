package com.example.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import akka.actor.typed.javadsl.Behaviors;
import lombok.SneakyThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SimpleMediatorRunnerImpl.class)
public final class SimpleMediatorRunnerTests {

    @Autowired
    private SimpleMediatorRunner runner;

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

        runner.spawn(simpleMediator, simpleMediatorInitialMessage);

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
        runner.spawn(firstMediator, ignoredMessage);
        runner.spawn(secondMediator, ignoredMessage);

        Assertions.assertThat(touched.get()).isTrue();
    }
}