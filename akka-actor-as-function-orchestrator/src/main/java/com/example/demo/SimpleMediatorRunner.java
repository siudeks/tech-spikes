package com.example.demo;

import java.util.UUID;

import org.springframework.stereotype.Component;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import lombok.Value;


public interface SimpleMediatorRunner {
    <T> void spawn(Behavior<T> behaviorToSpawn, T initialMessage);
}

@Component
class SimpleMediatorRunnerImpl implements SimpleMediatorRunner, AutoCloseable {

    private ActorSystem<CreateRequest<?>> actorSystem;
    
    public SimpleMediatorRunnerImpl() {
        var userGuardianBeh = Behaviors.<CreateRequest<?>> setup(ctx -> {
            return Behaviors.receiveMessage(msg -> this.create(ctx, msg));
        });
        actorSystem = ActorSystem.create(userGuardianBeh, "mediators");
    }

    private Behavior<CreateRequest<?>> create(ActorContext<CreateRequest<?>> ctx, CreateRequest<?> msg) {
        var randomName = UUID.randomUUID().toString();
        var mediator = ctx.spawn(msg.behavior, randomName);
        mediator.tell(msg.initialMessage);
        return Behaviors.same();
    }

    @Override
    public void close() throws Exception {
        actorSystem.terminate();
    }

    @Value
    private class CreateRequest<T> {
        private Behavior<T> behavior;
        private T initialMessage;
    }

    @Override
    public <T> void spawn(Behavior<T> behaviorToSpawn, T initialMessage) {
        var request = new CreateRequest<>(behaviorToSpawn, initialMessage);
        actorSystem.tell(request);
    }
}

