package com.example.springboot.entities;

import java.util.Objects;

public class Address {
	private final String country;
	private final String city;
	private final String street;

	public Address(String country, String city, String street) {
		this.country = country;
		this.city = city;
		this.street = street;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return Objects.equals(country, address.country) &&
				Objects.equals(city, address.city) &&
				Objects.equals(street, address.street);
	}

	@Override
	public int hashCode() {
		return Objects.hash(country, city, street);
	}

	@Override
	public String toString() {
		return "Address{" +
				"country='" + country + '\'' +
				", city='" + city + '\'' +
				", street='" + street + '\'' +
				'}';
	}
}
