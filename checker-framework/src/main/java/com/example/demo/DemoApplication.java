package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		String emailAsString = "my email";
		String addressAsString = "my address";
		@Email String email = emailAsString;
		@Address String address = addressAsString;
		SpringApplication.run(DemoApplication.class, args);
	}

}
