package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;

import akka.NotUsed;
import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.Behaviors;
import lombok.SneakyThrows;
import reactor.core.publisher.MonoProcessor;

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

        var terminationSignal = MonoProcessor.<NotUsed>create();
        testKit.spawn(TerminationWatcher.create(sut, terminationSignal));


        sut.tell(new MyMediator.Command.Verify("ignored", "ignored"));


        Assertions
            .assertThat(result.get())
            .isExactlyInstanceOf(MyMediator.Result.InternalTimeout.class);

        Assertions
            .assertThat(terminationSignal.blockOptional())
            .isNotEmpty();

    }

}

class TerminationWatcher {

    public static Behavior<Void> create(final ActorRef<?> watchedActor,
                                        final Subscriber<NotUsed> terminationMarker) {
                            
        return Behaviors.setup(ctx -> {
            ctx.watch(watchedActor);
            return Behaviors.receiveSignal((a, b) -> {
                if (b instanceof Terminated) {
                    terminationMarker.onNext(NotUsed.getInstance());
                };
                return Behaviors.stopped();
            });
        });
    }
}
