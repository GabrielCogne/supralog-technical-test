package com.example.springboot.controllers.dto;

import com.example.springboot.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UserRegistryDto {
    @NotBlank(message = "name should not be blank")
    public String name;

    @NotBlank(message = "email should not be blank")
    @Email
    public String email;

    @NotBlank(message = "password should not be blank")
    public String password;

    @NotNull(message = "dateOfBirth should not be null")
    public LocalDate dateOfBirth;

    @NotNull(message = "address should not be null")
    public AddressDto address;

    public String phoneNumber;

    public String description;

    @Override
    public String toString() {
        return "UserRegistryDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public User toObject() {
        User result = new User(name, email, password, dateOfBirth, address.toObject());
        result.setPhoneNumber(phoneNumber);
        result.setDescription(description);
        return result;
    }
}
