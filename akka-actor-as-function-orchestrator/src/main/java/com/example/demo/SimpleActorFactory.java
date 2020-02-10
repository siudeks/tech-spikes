package com.example.demo;

import java.util.UUID;

import org.springframework.stereotype.Component;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import lombok.Value;


public interface SimpleActorFactory {
    <T> void spawn(Behavior<?> startableBehavior);
}

@Component
class SimpleActorFactoryImpl implements SimpleActorFactory, AutoCloseable {

    private ActorSystem<CreateRequest> actorSystem;
    
    public SimpleActorFactoryImpl() {
        var userGuardianBeh = Behaviors.<CreateRequest> setup(ctx -> {
            return Behaviors.receiveMessage(msg -> this.create(ctx, msg));
        });
        actorSystem = ActorSystem.create(userGuardianBeh, "mediators");
    }

    private Behavior<CreateRequest> create(ActorContext<CreateRequest> ctx, CreateRequest msg) {
        var randomName = UUID.randomUUID().toString();
        ctx.spawn(msg.behavior, randomName);
        return Behaviors.same();
    }

    @Override
    public void close() throws Exception {
        actorSystem.terminate();
    }

    @Value
    private class CreateRequest {
        private Behavior<?> behavior;
    }

    @Override
    public <T> void spawn(Behavior<?> startableBehavior) {
        var request = new CreateRequest(startableBehavior);
        actorSystem.tell(request);
    }
}

