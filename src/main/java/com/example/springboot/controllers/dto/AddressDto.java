package com.example.springboot.controllers.dto;

import com.example.springboot.entities.Address;
import jakarta.validation.constraints.NotBlank;

public record AddressDto (@NotBlank(message = "country should not be blank") String country, String city, String street) {
	/**
	 * Transform the dto into an Address
	 */
	public Address toObject() {
		return new Address(country, city, street);
	}
}
