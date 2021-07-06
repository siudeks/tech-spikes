package net.siudek.dev.example2;

import java.util.concurrent.CompletableFuture;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import io.vavr.Function0;
import lombok.Value;

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