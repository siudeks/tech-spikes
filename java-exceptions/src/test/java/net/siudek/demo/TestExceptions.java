package net.siudek.demo;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;

public class TestExceptions {
    
    @Test
    public void shouldHandleExceptionAndRecognizeType() {
        var exceptionClass = IOException.class;
        Assertions
            .assertThatExceptionOfType(exceptionClass)
            .isThrownBy(() -> throwException(exceptionClass));
    }

    @SneakyThrows
    private void throwException(Class<? extends Throwable> exceptionClass) {
        var ctor = exceptionClass.getConstructor();
        var ex = ctor.newInstance();
        throw ex;
    }
}
