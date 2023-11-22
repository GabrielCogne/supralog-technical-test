package com.example.springboot.exceptions;

import java.util.NoSuchElementException;

public class NoSuchUserException extends NoSuchElementException {
	public NoSuchUserException() {
		super("The requested user doesn't exist");
	}
}
