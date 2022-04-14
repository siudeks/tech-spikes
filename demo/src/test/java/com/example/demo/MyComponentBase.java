package com.example.demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;

import lombok.SneakyThrows;

public abstract class MyComponentBase<T> {

    private Queue<Callable<T>> invocations;

    public void prepare(List<Callable<T>> invocations) {
        this.invocations = new LinkedList<>(invocations);
    }

    @SneakyThrows
    public T myMethod() {
        var invocation = invocations.poll();
        return invocation.call();
    }
}
