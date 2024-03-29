package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-ci.properties")
@EnableConfigurationProperties(PersonConfig.class)
public class ReadConfigurationFromPropertySourcesTests {

    @Autowired
    PersonConfig personConfig;

    @Test
    public void shouldReadConfiguration() {
        assertThat(personConfig.getName()).isEqualTo("slawomir");
        assertThat(personConfig.getSurname()).isEqualTo("siudek");
    }

    @ExtendedValue("${person.name}")
    private String personName;

    @Test
    public void shouldReadExtendedValue() {
        assertThat(personName).isEqualTo("slawomir");
    }

}
