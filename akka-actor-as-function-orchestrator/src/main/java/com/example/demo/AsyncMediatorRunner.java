package com.example.demo;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Component;

import akka.NotUsed;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.TimerScheduler;
import lombok.Value;

/** Allows to run given Behavior and send initial message. */
public interface AsyncMediatorRunner {
    <T> void spawn(Behavior<T> behaviorToSpawnAndRun, T initialMessage, Duration timeout);
}

@Component
class SimpleMediatorRunnerImpl implements AsyncMediatorRunner, AutoCloseable {

    /**
     * The only one purpose of the Actor System is to run Mediators.
     * <p>
     * actorSystem is not provide only to be used uin unit tests and it is not
     * intended to be used outside of {@link SimpleMediatorRunnerImpl}.
     */

    ActorSystem<CreateRequest<?>> actorSystem;

    public SimpleMediatorRunnerImpl() {
        var userGuardianBeh = Behaviors.<CreateRequest<?>>setup(ctx -> {
            return Behaviors.receiveMessage(msg -> this.create(ctx, msg));
        });
        actorSystem = ActorSystem.create(userGuardianBeh, "mediators");
    }

    private Behavior<CreateRequest<?>> create(ActorContext<CreateRequest<?>> ctx, CreateRequest<?> msg) {
        var randomName = UUID.randomUUID().toString();
        ctx.spawn(msg.initialBehavior, randomName);
        return Behaviors.same();
    }

    @Override
    public void close() throws Exception {
        actorSystem.terminate();
    }

    @Value
    private class CreateRequest<T> {
        private Behavior<T> initialBehavior;
    }

    @Override
    public <T> void spawn(final Behavior<T> behaviorToSpawn,
                          final T initialMessage,
                          final Duration timeout) {

        var parent = Behaviors.<NotUsed>setup(context -> {
            var inner = context.spawn(behaviorToSpawn, UUID.randomUUID().toString());
            inner.tell(initialMessage);
            return Behaviors.withTimers(SimpleMediatorRunnerImpl::stopAfterTimeout);
        });

        var request = new CreateRequest<>(parent);
        actorSystem.tell(request);
    }

    private static Behavior<NotUsed> stopAfterTimeout(final TimerScheduler<NotUsed> msg) {
        return Behaviors.stopped();
    }
}
