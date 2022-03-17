package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class MyBeanDefaultTest {

    @Autowired
    MyBean myBean;

    @Test
    public void shouldCreateDefaultBean() {
        Assertions.assertThat(myBean.text).isEqualTo("my default bean");
    }

    @Test
    public void shouldNotInstaniateFallbackBean() {
        Assertions.assertThat(myBean.testCounter).isOne();
    }
}
