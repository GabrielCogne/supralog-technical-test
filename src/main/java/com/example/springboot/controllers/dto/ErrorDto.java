package com.example.springboot.controllers.dto;

public class ErrorDto {
	private final String error;

	public ErrorDto(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	@Override
	public String toString() {
		return "ErrorDto (" + error + ")";
	}
}
