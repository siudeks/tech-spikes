package com.example.demo;

import java.util.Optional;

import com.example.demo.qual.Address;
import com.example.demo.qual.Email;

public class Program {

	public static void main(@Email String[] args) {
		var result = myMethod1(args[0], (@Address String) args[1]);
		@Address String s = result.get();
	}

	static Optional<@Email String> myMethod1(@Email String email, @Address String address) {
		return Optional.of(email);
	}
}
