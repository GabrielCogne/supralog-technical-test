package com.example.springboot.controllers;

import com.example.springboot.controllers.dto.AddressDto;
import com.example.springboot.controllers.dto.UserRegistryDto;
import com.example.springboot.entities.User;
import com.example.springboot.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserRepository userRepository;

	private UserRegistryDto testUser;

	@BeforeEach
	void setup() {
		testUser = new UserRegistryDto();
		testUser.name = "La buse";
		testUser.email = "olivier.levasseur@something.ex";
		testUser.password = "****";
		testUser.dateOfBirth = LocalDate.of(1965, 11, 5);
		testUser.address = new AddressDto("France", "", "");
	}

	@Test
	void validUserRegistry() throws Exception {
		when(userRepository.save(ArgumentMatchers.any(User.class))).then(invocationOnMock -> invocationOnMock.getArgument(0));

		mockMvc.perform(
				MockMvcRequestBuilders.post(UserController.BASE_URL + "/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void registrationFailureDueToAge() throws Exception {
		testUser.dateOfBirth = LocalDate.now().minusYears(17);
		testUser.address = new AddressDto("France", "", "");
		mockMvc.perform(
						MockMvcRequestBuilders.post(UserController.BASE_URL + "/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void registrationFailureDueToAddress() throws Exception {
		testUser.dateOfBirth = LocalDate.of(1965, 11, 5);
		testUser.address = new AddressDto("England", "", "");
		mockMvc.perform(
						MockMvcRequestBuilders.post(UserController.BASE_URL + "/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void getUserDetails() throws Exception {
		when(userRepository.findById(ArgumentMatchers.eq(BigInteger.ONE))).thenReturn(Optional.of(testUser.toObject()));

		String response = mockMvc.perform(MockMvcRequestBuilders.get(UserController.BASE_URL + "/" + BigInteger.ONE))
				.andReturn().getResponse().getContentAsString();

		assertEquals(testUser.toObject(), objectMapper.readValue(response, User.class));
	}

	@Test
	void userNotFound() throws Exception {
		when(userRepository.findById(ArgumentMatchers.eq(BigInteger.TWO))).thenReturn(Optional.empty());

		mockMvc.perform(MockMvcRequestBuilders.get(UserController.BASE_URL + "/" + BigInteger.TWO))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
