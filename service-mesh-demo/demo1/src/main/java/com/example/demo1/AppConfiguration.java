package com.example.demo1;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
	@Bean
	WebClient webClient() {
		return WebClient.create();
	}
}
