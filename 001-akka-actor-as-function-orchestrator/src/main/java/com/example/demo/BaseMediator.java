package com.example.demo;

import java.util.concurrent.CompletableFuture;

public abstract class BaseMediator<CMD, RESULT> {

    protected BaseMediator(final CompletableFuture<RESULT> result,
                           final RESULT fallbackValue) {
    }
}

