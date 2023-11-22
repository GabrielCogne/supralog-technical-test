package com.example.springboot.exceptions;

public class UnauthorizedUserCreationException extends Exception {
	public UnauthorizedUserCreationException(String reason) {
		super(reason);
	}

	@Override
	public String toString() {
		return this.getMessage();
	}
}
