package net.siudek.dev.orchestractor;

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
import reactor.core.publisher.Sinks;

/**
 * When using short-living actors designed to handle a single request and produce single response
 * as the result of more internal async operations, we would like to simplify as much as possible
 * - starting such actors in a simple way, and
 * - avoiding memory leak (in case of someone who forgot to destroy the actor after all)
 */
public final class MediatorRunnerTests {

  // system under test (hereafter: sut)
  private BehSupportImpl sut = new BehSupportImpl();

  /** A simple behavior should be started by BehSupport. */
  @Test
  public void shouldRunSimpleBehavior() {

    var touched = new CompletableFuture<Boolean>()
        .completeOnTimeout(Boolean.FALSE, 100, TimeUnit.MILLISECONDS);

    var simpleBeh = Behaviors.setup(ctx -> {
      touched.complete(Boolean.TRUE);
      return Behaviors.stopped();
    });
    var emptyTimoutHandler = (Runnable) () -> { };
    var timeoutLongEnouthToRunSimpleLogic = Duration.ofSeconds(1);
    sut.run(simpleBeh, emptyTimoutHandler, timeoutLongEnouthToRunSimpleLogic);

    Assertions.assertThat(touched.join()).isTrue();
  }

  /** A simple behavior should be terminated and timeout handler shoudl be invoked. */
  @Test
  public void shouldTerminateBehaviorAfterTimeout() {
    var postStopSignaled = Sinks.one();
    var myBehavior = Behaviors.<NotUsed>setup(ctx -> {
      return Behaviors
        .receive(NotUsed.class)
        .onSignal(PostStop.class, signal -> {
          postStopSignaled.tryEmitValue(NotUsed.getInstance());
          return Behaviors.stopped();
        })
        .build();
    });
                    
    var terminationSignaled = Sinks.<NotUsed>one();
    sut.run(myBehavior,
            () -> terminationSignaled.tryEmitValue(NotUsed.getInstance()),
            Duration.ofSeconds(0));

    assertThat(Mono
      .zip(postStopSignaled.asMono(), terminationSignaled.asMono())
      .timeout(Duration.ofMillis(300))
      .blockOptional())
        .isNotEmpty();
  }

  @Test
  public void shouldNotNotifyWhenNotTimeouted() {
    var terminationSignaled = Sinks.<Boolean>one();
    sut.run(Behaviors.stopped(),
                () -> terminationSignaled.tryEmitValue(true),
                Duration.ofMillis(300));

    assertThat(terminationSignaled.asMono()
      .timeout(Duration.ofMillis(300), Mono.just(false))
      .blockOptional())
        .hasValue(false);
  }
}
