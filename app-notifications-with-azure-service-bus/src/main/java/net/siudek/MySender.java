package net.siudek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.GenericMessage;

@EnableBinding(MySource.class)
public class MySender {

    @Autowired
    private MySource source;

    public void sendMessage(String message) {
        this.source.output().send(new GenericMessage<>(message));
    }
}