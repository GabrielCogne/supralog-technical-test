package com.example.springboot.components;

import com.example.springboot.entities.Address;
import com.example.springboot.entities.User;
import com.example.springboot.exceptions.UnauthorizedUserCreationException;
import com.example.springboot.interfaces.UserRegistry;
import com.example.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserRegistryTest {

	@MockBean
	private UserRepository repository;

	@Autowired
	private UserRegistry userRegistry;

	/**
	 * Return a date that is {years} before today
	 * @param years Time (in years) before today
	 */
	static LocalDate getDateYearsAgo(int years) {
		return LocalDate.now().minusYears(years);
	}

	/**
	 * Return an address inside a country
	 * @param country the country of residence
	 */
	static Address getAddressIn(String country) {
		return new Address(country, "", "");
	}

	@BeforeEach
	void setup() {
		when(repository.save(ArgumentMatchers.any(User.class))).then(invocationOnMock -> invocationOnMock.getArgument(0));
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
		assertDoesNotThrow(() -> userRegistry.registerUser(frenchAdult, false));
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
		assertThrows(UnauthorizedUserCreationException.class, () -> userRegistry.registerUser(notAdultPerson, false));
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
		assertThrows(UnauthorizedUserCreationException.class, () -> userRegistry.registerUser(notFrenchPerson, false));
	}
}
