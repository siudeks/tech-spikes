package com.example.demo;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Component;

import akka.NotUsed;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.TimerScheduler;
import lombok.Value;

/** Allows to spawn given Behavior and send initial message. */
@FunctionalInterface
public interface BehSupport {
    
    void spawn(Behavior<?> behaviorToSpawnAndRun, Runnable timeoutHandler, Duration timeout);
}

@Component
class BehSupportImpl implements BehSupport, AutoCloseable {

    /**
     * The only one purpose of the Actor System is to run Mediators.
     * <p>
     * actorSystem is not provide only to be used uin unit tests and it is not
     * intended to be used outside of {@link BehSupportImpl}.
     */

    ActorSystem<CreateRequest<?>> actorSystem;

    public BehSupportImpl() {
        var userGuardianBeh = Behaviors.<CreateRequest<?>>setup(ctx -> {
            return Behaviors.receiveMessage(msg -> this.create(ctx, msg));
        });
        actorSystem = ActorSystem.create(userGuardianBeh, "mediators");
    }

    private Behavior<CreateRequest<?>> create(ActorContext<CreateRequest<?>> ctx, CreateRequest<?> msg) {
        ctx.spawnAnonymous(msg.initialBehavior);
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
    public void spawn(final Behavior<?> behaviorToSpawn,
                      final Runnable timeoutHandler,
                      final Duration timeout) {

        var request = new CreateRequest<>(SpawnBehavior.create(behaviorToSpawn, timeoutHandler, timeout));
        actorSystem.tell(request);
    }
}


final class SpawnBehavior extends AbstractBehavior<NotUsed> {

    private Duration timeout;
    private Runnable timeoutHandler;
    private SpawnBehavior(final ActorContext<NotUsed> ctx,
                          final Behavior<?> behaviorToSpawn,
                          final Runnable timeoutHandler,
                          final Duration timeout) {
        super(ctx);

        this.timeout = timeout;
        this.timeoutHandler = timeoutHandler;
        
        var watched = ctx.spawn(behaviorToSpawn, UUID.randomUUID().toString());
        ctx.watch(watched);
        ctx.getSelf().tell(NotUsed.getInstance());
    }

    static Behavior<NotUsed> create(final Behavior<?> behaviorToSpawn,
                                    final Runnable timeoutHandler,
                                    final Duration timeout) {
        return Behaviors.setup(ctx -> new SpawnBehavior(ctx,
                                                        behaviorToSpawn,
                                                        timeoutHandler,
                                                        timeout));
    }

    @Override
    public Receive<NotUsed> createReceive() {
        return newReceiveBuilder()
            .onMessage(NotUsed.class, this::start)
            .onSignal(Terminated.class, this::whenWatchedTerminated)
            .build();
    }

    private Behavior<NotUsed> start(final NotUsed ignored) {
        return Behaviors.withTimers(this::stopAfterTimeout);
    }

    private Behavior<NotUsed> stopAfterTimeout(final TimerScheduler<NotUsed> timers) {
        timers.startSingleTimer(NotUsed.getInstance(), timeout);
        return Behaviors
            .receive(NotUsed.class)
            .onMessage(NotUsed.class, this::onTimeout)
            .onSignal(Terminated.class, this::whenWatchedTerminated)
            .build();
    }

    private Behavior<NotUsed> onTimeout(final NotUsed ignored) {
        timeoutHandler.run();
        return Behaviors.stopped();
    }

    // the only one case when 'Terminated' signal can be invoked is when
    // watched behavior has been finished on time (properly)
    // so that the guard is ready to finish work without informing about timeout.
    private Behavior<NotUsed> whenWatchedTerminated(Terminated signal) {
        return Behaviors.stopped();
    }
}

