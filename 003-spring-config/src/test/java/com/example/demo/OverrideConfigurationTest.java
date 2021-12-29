package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.support.TestPropertySourceUtils.addInlinedPropertiesToEnvironment;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(
  initializers = PropertyOverrideContextInitializer.class,
  classes = DemoApplication.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OverrideConfigurationTest {

    @Autowired
    PersonConfig personConfig;

    @Autowired
    PersonBasedService service;

    @Test
    public void shouldOverrideConfiguration() {
        var actual = service.getName();
        var expected = "default name New Surname";
        assertThat(actual).isEqualTo(expected);
    }

}

class PropertyOverrideContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static final String PROPERTY_FIRST_VALUE = "contextClass";

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
         addInlinedPropertiesToEnvironment(ctx, "person.surname=New Surname");
    }
}

