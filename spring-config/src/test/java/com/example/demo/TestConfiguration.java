package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-ci.properties")
@EnableConfigurationProperties(PersonConfig.class)
@ContextConfiguration(
    classes = { PersonConfig.class})
public class TestConfiguration {

    @Autowired
    PersonConfig personConfig;

    @Test
    public void shouldReadConfiguration() {
        assertThat(personConfig.getName()).isEqualTo("slawomir");
        assertThat(personConfig.getSurname()).isEqualTo("siudek");
    }
}
