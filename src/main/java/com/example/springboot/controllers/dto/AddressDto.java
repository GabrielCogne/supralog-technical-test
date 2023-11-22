package com.example.springboot.controllers.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDto (@NotBlank(message = "country should not be blank") String country, String city, String street) {
}
