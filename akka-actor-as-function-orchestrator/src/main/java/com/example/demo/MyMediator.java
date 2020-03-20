package com.example.demo;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.TimerScheduler;
import lombok.Value;

public class MyMediator {

    private final CompletableFuture<Result> operationResult;
    private TimerScheduler<Command> timers;
    private VerificationService.Do service;
    private Duration durationForCompletion;

    private MyMediator(final CompletableFuture<Result> resultHandler,
                       final TimerScheduler<Command> timers,
                       final Duration timeout,
                       final VerificationService.Do service) {

        this.operationResult = resultHandler;
        this.timers = timers;
        this.service = service;
        this.durationForCompletion = timeout;
    }

    /**
     * Method to create initial instance of the behavior.
     * @return Behavior of just created instance.
     */
    public static Behavior<MyMediator.Command> create(
                  final CompletableFuture<Result> resultHandler,
                  final Duration timeout,
                  final VerificationService.Do service) {

      return Behaviors.withTimers(timers -> new MyMediator(resultHandler, timers, timeout, service).inactive());
    }

    private Behavior<Command> inactive() {
        timers.startSingleTimer(new Object(), Timeout.INSTANCE, durationForCompletion);
        return Behaviors.receive(Command.class)
            .onMessage(Timeout.INSTANCE.getClass(), this::timeouted)
            .build();
    }

    private Behavior<Command> timeouted(Timeout ignored) {
        this.operationResult.complete(new Result.InternalTimeout());
        return Behaviors.stopped();
    }
    
    /** Initial message to start operations. */
    public interface Command {

        @Value
        public static class Verify implements Command {
            private String userName;
            private String userPassword;
        }

    }

    public abstract static class Result {

        private Result() { }

        public static final class InternalTimeout extends Result {

        }
    }


    private enum Timeout implements Command {
        INSTANCE
    }

}