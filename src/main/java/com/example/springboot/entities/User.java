package com.example.springboot.entities;

import java.util.Calendar;
import java.util.Objects;

public class User {
	private int id;

	private final String name;
	private final String email;
	private final Calendar dateOfBrith;
	private final Address address;
	private String phoneNumber;
	private String description;

	public User(String name, String email, Calendar dateOfBrith, Address address) {
		this.name = name;
		this.email = email;
		this.dateOfBrith = dateOfBrith;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Calendar getDateOfBrith() {
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

	public void setId(int id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @param date Another user date of birth as Calendar
	 * @return Is the other user born the same year, month and day of the month
	 */
	private boolean isBornTheSameDay(Calendar date) {
		return dateOfBrith.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
				dateOfBrith.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
				dateOfBrith.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(name, user.name) &&
				Objects.equals(email, user.email) &&
				isBornTheSameDay(user.dateOfBrith) &&
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
