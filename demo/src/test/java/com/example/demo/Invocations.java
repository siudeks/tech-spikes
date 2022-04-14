package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Invocations {
    
    public static <T> List<Callable<T>> of(Callable<T>... invocations) {
        return Arrays.asList(invocations);
    }
}
