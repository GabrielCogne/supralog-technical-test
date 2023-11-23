package com.example.springboot.entities;

import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

public class User {
	@Id
	private BigInteger id;

	private final String name;
	private final String email;

	private final String password;

	private final LocalDate dateOfBrith;
	private final Address address;
	private String phoneNumber;
	private String description;

	public User(String name, String email, String password, LocalDate dateOfBrith, Address address) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.dateOfBrith = dateOfBrith;
		this.address = address;
	}

	public BigInteger getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getDateOfBrith() {
		return dateOfBrith;
	}

	public Address getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(name, user.name) &&
				Objects.equals(email, user.email) &&
				Objects.equals(dateOfBrith, user.dateOfBrith) &&
				Objects.equals(address, user.address) &&
				Objects.equals(phoneNumber, user.phoneNumber) &&
				Objects.equals(description, user.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, email, dateOfBrith, address, phoneNumber, description);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", dateOfBrith=" + dateOfBrith +
				", address=" + address +
				", phoneNumber='" + phoneNumber + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
