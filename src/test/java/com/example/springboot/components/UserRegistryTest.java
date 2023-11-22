package com.example.springboot.components;

import com.example.springboot.entities.Address;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.UnauthorizedUserCreationException;
import com.example.springboot.interfaces.UserRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserRegistryTest {

	@Autowired
	private UserRegistry userRegistry;

	/**
	 * Return a date that is {years} before today
	 * @param years Time (in years) before today
	 */
	static Calendar getDateYearsAgo(int years) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -years);
		return c;
	}

	/**
	 * Return an address inside a country
	 * @param country the country of residence
	 */
	static Address getAddressIn(String country) {
		return new Address(country, "", "");
	}

	@Test
	void registerUser() {
		User frenchAdult = new User(
				"Olivier",
				"olivier.levasseur@something.ex",
				"****",
				getDateYearsAgo(18),
				getAddressIn("France")
		);
		assertDoesNotThrow(() -> userRegistry.registerUser(frenchAdult));
	}

	@Test
	void ageRegistryFailure() {
		User notAdultPerson = new User(
				"Olivier",
				"olivier.levasseur@something.ex",
				"****",
				getDateYearsAgo(17),
				getAddressIn("France")
		);
		assertThrows(UnauthorizedUserCreationException.class, () -> userRegistry.registerUser(notAdultPerson));
	}

	@Test
	void addressRegistryFailure() {
		User notFrenchPerson = new User(
				"Henri",
				"henri.morgan@something.ex",
				"****",
				getDateYearsAgo(18),
				getAddressIn("Wales")
		);
		assertThrows(UnauthorizedUserCreationException.class, () -> userRegistry.registerUser(notFrenchPerson));
	}
}
