package net.siudek.dev.orchestractor;

import java.time.Duration;
import java.util.UUID;

import akka.NotUsed;
import akka.actor.typed.Behavior;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.TimerScheduler;

final class RunBehavior extends AbstractBehavior<NotUsed> {

  private Duration timeout;
  private Runnable timeoutHandler;

  private RunBehavior(final ActorContext<NotUsed> ctx,
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
    return Behaviors.setup(ctx -> new RunBehavior(ctx,
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

