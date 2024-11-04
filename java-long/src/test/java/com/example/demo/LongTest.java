package com.example.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import lombok.SneakyThrows;

public class LongTest {

    @Test
    @SneakyThrows
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void aaa() throws InterruptedException {
        var counter = new LongAdder();
        var executorService = Executors.newFixedThreadPool(8);

        int numberOfThreads = 4;
        int numberOfIncrements = 100;

        Runnable incrementAction = () -> IntStream
                .range(0, numberOfIncrements)
                .forEach(i -> counter.increment());

        var tasks = IntStream
                .range(0, numberOfThreads).mapToObj(index -> incrementAction)
                .map(Executors::callable)
                .toList();

        var waiter = executorService.invokeAll(tasks);
        for (var task : waiter)
            task.get();

        Assertions.assertThat(counter.sum()).isEqualTo(numberOfIncrements * numberOfThreads);
    }
}
