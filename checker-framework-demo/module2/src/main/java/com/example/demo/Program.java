package com.example.demo;

import java.util.Optional;

import com.example.demo.qual.Address;
import com.example.demo.qual.Email;

public class Program {

	public static void main(@Email String[] args) {
		var emailArg = args[0];
		var result = myMethod1(emailArg, emailArg);
		@Address String asAddress = result.get();
	}

	static Optional<@Email String> myMethod1(@Email String email, @Address String address) {
		return Optional.of(email);
	}
}
