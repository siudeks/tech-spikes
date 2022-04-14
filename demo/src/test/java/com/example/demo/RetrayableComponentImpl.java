package com.example.demo;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class RetrayableComponentImpl extends MyComponentBase<Integer> {

    @Retryable
    @Override
    public Integer myMethod() {
        return super.myMethod();
    }
}
