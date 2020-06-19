package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.Behaviors;
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

        var result = new CompletableFuture<MyMediator.Result>();

        var nonRespondingService = (VerificationService.Do) (claim) -> new CompletableFuture<>();

        var beh = MyMediator.create(result, Duration.ZERO, nonRespondingService);
        var sut = testKit.spawn(beh);

        var terminationSignal = new AtomicBoolean();
        testKit.spawn(TerminationWatcher.create(sut, terminationSignal));


        sut.tell(new MyMediator.Command.Verify("ignored", "ignored"));


        Assertions
            .assertThat(result.get())
            .isExactlyInstanceOf(MyMediator.Result.InternalTimeout.class);

        Assertions
            .assertThat(terminationSignal.get())
            .isTrue();

    }

    // @Test
    // public void shouldAllowToHandleMessage() {
    //     var myMessage = new MyCommand() {};
    //     var myResult = new MyResult() {};
    //     var handler = Behaviors
    //         .receive(MyCommand.class)
    //         .onMessage(myMessage.getClass(), s -> Behaviors.same())
    //         .build();
            
    //     var mediator = MyMediator.create(resultHandler, timeout, service);
    // }

    interface MyCommand {
    }

    interface MyResult {
    }
}

class TerminationWatcher {

    public static Behavior<Void> create(final ActorRef<?> watchedActor,
                                        final AtomicBoolean terminationMarker) {
                            
        return Behaviors.setup(ctx -> {
            ctx.watch(watchedActor);
            return Behaviors.receiveSignal((a, b) -> {
                if (b instanceof Terminated) {
                    terminationMarker.set(true);
                };
                return Behaviors.stopped();
            });
        });
    }
}
