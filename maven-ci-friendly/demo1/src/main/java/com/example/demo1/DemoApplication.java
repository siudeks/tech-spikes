package com.example.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class DemoApplication {

	@Autowired
	WebClient webClient;

	@GetMapping(path = "/")
	public Mono<String> get() {
		var message = "Service1";
		return Mono.just(message);
	}

	@GetMapping(path = "/status")
	public Mono<String> streamFlux() {
		return webClient.get()
			.exchangeToMono(r -> r.bodyToMono(String.class))
			.map(it -> "Service 2 message: " + it)
			.onErrorResume(it -> true, it -> Mono.just("Service2 DOWN"));
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
