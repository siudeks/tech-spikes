package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class Demo2Application {

	@GetMapping(path = "/")
	public Mono<String> get() {
		var message = "Service2";
		return Mono.just(message);
	}

	public static void main(String[] args) {
		SpringApplication.run(Demo2Application.class, args);
	}

}
