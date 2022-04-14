package com.example.demo;

import static com.example.demo.Invocations.of;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RetrayableComponentImpl.class, DefaultRetryTest.LocalConfiguration.class })
class DefaultRetryTest {

	@Autowired
	RetrayableComponentImpl myComponent;

	@Test
	void shouldHandleRetry() {
		var invocations = of(
			() -> { throw new IllegalArgumentException(); },
			() -> 1);
		myComponent.prepare(invocations);

		assertThat(myComponent.myMethod()).isEqualTo(1);
	}

	@EnableRetry
	@TestConfiguration
	static class LocalConfiguration {
	}

}

