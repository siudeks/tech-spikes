package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("myprofile")
public class ReadConfigurationFromProfileTests {

    @Autowired
    PersonConfig personConfig;

    @Test
    public void shouldReadConfiguration() {
        assertThat(personConfig.getName()).isEqualTo("slawomir1");
        assertThat(personConfig.getSurname()).isEqualTo("siudek1");
    }
}
