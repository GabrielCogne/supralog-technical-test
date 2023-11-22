package com.example.springboot.components;

import com.example.springboot.entities.User;
import com.example.springboot.exceptions.NoSuchUserException;
import com.example.springboot.interfaces.UserFinder;
import com.example.springboot.interfaces.UserRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.springboot.components.UserRegistryTest.getAddressIn;
import static com.example.springboot.components.UserRegistryTest.getDateYearsAgo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserFinderTest {

	@Autowired
	private UserFinder userList;

	@Autowired
	private UserRegistry userRegistry;

	private int id;

	@BeforeEach
	void setup() throws Exception {
		User frenchAdult = new User(
				"Olivier",
				"olivier.levasseur@something.ex",
				"****",
				getDateYearsAgo(18),
				getAddressIn("France")
		);
		this.id = userRegistry.registerUser(frenchAdult);
	}

	@Test
	void getUserDetails() {
		User user = userList.getUserDetails(id);

		assertEquals(
				new User(
						"Olivier",
						"olivier.levasseur@something.ex",
						"****",
						getDateYearsAgo(18),
						getAddressIn("France")
				),
				user
		);
	}

	@Test
	void userNotFound() {
		assertThrows(NoSuchUserException.class, () -> userList.getUserDetails(id + 1));
	}
}
