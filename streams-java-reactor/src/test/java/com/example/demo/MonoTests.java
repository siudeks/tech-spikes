package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;

public final class MonoTests {

    @Test
    public void shouldZip_case1() {
        var mono1 = Mono.<Integer>just(1);
        var delayed2 = new CompletableFuture<Integer>().completeOnTimeout(2, 300, TimeUnit.MILLISECONDS);
        var mono2 = Mono.fromCompletionStage(delayed2);

        var agg = Mono.zip(
            mono1,
            mono2)
        .map(v -> {
            return v.getT1() + v.getT2(); 
        })
        .defaultIfEmpty(0)
        .block();

        assertThat(agg).isEqualTo(3);
    }

    @Test
    public void shouldZip_case2() {
        var mono1 = Mono.<Integer>just(1);
        var delayed2 = new CompletableFuture<Integer>().completeOnTimeout(2, 300, TimeUnit.MILLISECONDS);
        var mono2 = Mono.fromCompletionStage(delayed2);

        var agg = Mono.zip(
            mono1,
            mono2)
        .map(v -> {
            return v.getT1() + v.getT2(); 
        })
        .defaultIfEmpty(0)
        .toFuture().join();

        assertThat(agg).isEqualTo(3);
    }

    @Test
    public void shouldZip_case3() {
        var mono1 = Mono.<Integer>just(1);
        var delayed2 = new CompletableFuture<Integer>().completeOnTimeout(2, 300, TimeUnit.MILLISECONDS);
        var mono2 = Mono.fromCompletionStage(delayed2);
        var delayed3 = new CompletableFuture<Integer>().completeOnTimeout(3, 1000, TimeUnit.MILLISECONDS);
        var mono3 = Mono.fromCompletionStage(delayed3);

        var agg = Mono.zip(
            mono1,
            mono2,
            mono3)
        .map(v -> v.getT1() + v.getT2() + v.getT3())
        .defaultIfEmpty(0)
        .toFuture().join();

        assertThat(agg).isEqualTo(6);
    }
}
