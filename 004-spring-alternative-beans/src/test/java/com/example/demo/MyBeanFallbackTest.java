package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class MyBeanFallbackTest {

    @Autowired
    MyBean myBean;

    @Test
    public void shouldCreateFallbackBean() {
        Assertions.assertThat(myBean.text).isEqualTo("my fallback bean");
    }
}
