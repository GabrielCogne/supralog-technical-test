package com.example.springboot.components;

import com.example.springboot.entities.User;
import com.example.springboot.exceptions.NoSuchUserException;
import com.example.springboot.interfaces.UserFinder;
import com.example.springboot.interfaces.UserRegistry;
import com.example.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigInteger;
import java.util.Optional;

import static com.example.springboot.components.UserRegistryTest.getAddressIn;
import static com.example.springboot.components.UserRegistryTest.getDateYearsAgo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserFinderTest {

	@MockBean
	private UserRepository repository;

	@Autowired
	private UserFinder userList;

	private BigInteger id;

	@BeforeEach
	void setup() throws Exception {
		User frenchAdult = new User(
				"Olivier",
				"olivier.levasseur@something.ex",
				"****",
				getDateYearsAgo(18),
				getAddressIn("France")
		);
		this.id = BigInteger.ONE;
		when(repository.findById(ArgumentMatchers.eq(this.id))).thenReturn(Optional.of(frenchAdult));
		when(repository.findById(ArgumentMatchers.eq(this.id.add(BigInteger.ONE)))).thenReturn(Optional.empty());
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
		assertThrows(NoSuchUserException.class, () -> userList.getUserDetails(id.add(BigInteger.ONE)));
	}
}
