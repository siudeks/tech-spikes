package com.example.demo;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import lombok.Value;

public abstract class VerificationService {

    public interface Do extends Function<Claims, CompletionStage<Result>> {

    }

    @Value
    public class Claims {
        private String name;
        private String password;
    }

    public interface Result {
    }

    @Value
    public static class Valid implements Result {
    }

    @Value
    public static class Invalid implements Result {
    }
}