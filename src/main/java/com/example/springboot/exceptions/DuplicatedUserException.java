package com.example.springboot.exceptions;

public class DuplicatedUserException extends Exception {
	public DuplicatedUserException() {
		super("The user is already registered");
	}
}
