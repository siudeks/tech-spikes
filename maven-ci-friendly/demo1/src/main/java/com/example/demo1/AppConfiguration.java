package com.example.demo1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfiguration {
	@Value("${SERVICE2_ADDRESS}")
	private String service2;

	@Bean
	WebClient webClient() {
		return WebClient.create(service2);
	}
}
