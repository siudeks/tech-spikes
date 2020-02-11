package com.example.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import akka.actor.typed.javadsl.Behaviors;
import lombok.SneakyThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SimpleMediatorRunnerImpl.class)
public final class SimpleMediatorRunnerTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @SneakyThrows
    public void shouldRunSimpleBehavior() {
        
        var runner = applicationContext.getBean(SimpleMediatorRunner.class);
        Assumptions.assumeThat(runner).isNotNull();

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
}