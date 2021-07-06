package net.siudek.dev.example2;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.vavr.Function0;
import net.siudek.dev.orchestractor.BehSupport;
import reactor.core.publisher.Mono;

/** TBD. */
@RestController
public class MyControllerB {

  @Autowired
  private BehSupport runner;

  /** TBD. */
  @GetMapping(path = "/b1")
  public CompletionStage<String> hello1() {

    var result = new CompletableFuture<String>();
        
    var service = Function0.of(() -> Mono.just(42).delayElement(Duration.ofSeconds(3)).toFuture());

    runner.run(MyBehaviorB.create(result, service), () -> {
      result.complete("empty!");
    }, Duration.ofSeconds(5));

    return result.thenApply(it -> it.toString());
  }

  /** TBD. */
  @GetMapping(path = "/b2")
  public CompletionStage<String> hello2() {

    var result = new CompletableFuture<String>();
        
    var service = Function0.of(() -> CompletableFuture.<Integer>failedFuture(new UnsupportedOperationException()));

    runner.run(MyBehaviorB.create(result, service), () -> {
      result.complete("empty!");
    }, Duration.ofSeconds(5));

    return result.thenApply(it -> it.toString());
  }
}

