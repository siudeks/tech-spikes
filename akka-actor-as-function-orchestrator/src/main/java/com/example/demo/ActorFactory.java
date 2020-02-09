package com.example.demo;

import org.springframework.stereotype.Component;


public interface ActorFactory {

}

@Component
class ActorFactoryImpl implements ActorFactory, AutoCloseable {

    public ActorFactoryImpl() {
        // final var sys = .create(ProlificParent.create(), "sys");
        // sys.tell(new ProlificParent.MakeNewChild());
    }

    @Override
    public void close() throws Exception {
    }

}