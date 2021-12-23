package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@TestPropertySource(properties = "-my-variable=value")
public class MyBeanDefaultTest {

    @Autowired
    MyBean myBean;

    @Test
    public void shouldCreateDefaultBean() {
        Assertions.assertThat(myBean.text).isEqualTo("my default bean");
    }
}
