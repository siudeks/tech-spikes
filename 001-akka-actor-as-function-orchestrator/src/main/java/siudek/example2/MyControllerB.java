package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import io.vavr.Function0;
import lombok.Value;
import reactor.core.publisher.Mono;

@RestController
public class MyControllerB {

  @Autowired
  private BehSupport runner;

  @GetMapping(path = "/b1")
  public CompletionStage<String> hello1() {

    var result = new CompletableFuture<String>();
        
    var service = Function0.of(() -> Mono.just(42).delayElement(Duration.ofSeconds(3)).toFuture());

    runner.spawn(MyBehaviorB.create(result, service), () -> {
      result.complete("empty!");
    }, Duration.ofSeconds(5));

    return result.thenApply(it -> it.toString());
  }

  /** TBD. */
  @GetMapping(path = "/b2")
  public CompletionStage<String> hello2() {

    var result = new CompletableFuture<String>();
        
    var service = Function0.of(() -> CompletableFuture.<Integer>failedFuture(new UnsupportedOperationException()));

    runner.spawn(MyBehaviorB.create(result, service), () -> {
      result.complete("empty!");
    }, Duration.ofSeconds(5));

    return result.thenApply(it -> it.toString());
  }
}

class MyBehaviorB extends AbstractBehavior<MyBehaviorB.Command> {

  interface Command {
  }

  enum Error implements Command {
    INSTANCE
  }

  @Value
  class Response implements Command {
    private int value;
  }

  private final CompletableFuture<String> result;

  private MyBehaviorB(ActorContext<Command> ctx,
                      CompletableFuture<String> result,
                      Function0<CompletableFuture<Integer>> service) {
    super(ctx);
    this.result = result;
    ctx.pipeToSelf(service.apply(), (v, ex) -> ex != null ? Error.INSTANCE : new Response(v));
  }

  static Behavior<Command> create(CompletableFuture<String> result,
                                  Function0<CompletableFuture<Integer>> service) {
    return Behaviors.setup(ctx -> new MyBehaviorB(ctx, result, service));
  }

  @Override
  public Receive<Command> createReceive() {
    return newReceiveBuilder()
        .onMessage(Response.class, this::onResponse)
        .onMessage(Error.class, this::onError)
        .build();            
  }

  private Behavior<Command> onResponse(Response cmd) {
    result.complete("Value: " + cmd.value);
    return Behaviors.stopped();
  }

  private Behavior<Command> onError(Error cmd) {
    result.complete("Error :(");
    return Behaviors.stopped();
  }
}