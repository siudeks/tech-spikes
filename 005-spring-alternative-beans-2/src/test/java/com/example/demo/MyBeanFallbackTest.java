package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@TestPropertySource(properties = "my-variable=false")
public class MyBeanFallbackTest {

    @Autowired
    MyBean myBean;

    @Test
    public void shouldCreateFallbackBean() {
        Assertions.assertThat(myBean.text).isEqualTo("my fallback bean");
    }
}
