package net.siudek.spike;

import java.util.LinkedList;
import java.util.Queue;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import lombok.SneakyThrows;
import lombok.val;

@Component
public class Sender implements AutoCloseable {

    Queue<AutoCloseable> disposables = new LinkedList<>();

    @PostConstruct
    public void init() {
        val c = initConnection();
        disposables.add(c);

        c.publish(subject, replyTo, body);
    }

    @Override
    public void close() throws Exception {
        for (val d : disposables)
            d.close();
    }

    @SneakyThrows
    private Connection initConnection() {
        val options = new Options.Builder().server("nats://demo.nats.io").build();

        return Nats.connect(options);
    }
}