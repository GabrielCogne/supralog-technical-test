package com.example.springboot.controllers;

import com.example.springboot.controllers.dto.ErrorDto;
import com.example.springboot.entities.Address;
import com.example.springboot.exceptions.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.controllers.dto.UserDetailDto;
import com.example.springboot.controllers.dto.UserRegistryDto;
import com.example.springboot.entities.User;
import com.example.springboot.interfaces.UserFinder;
import com.example.springboot.interfaces.UserRegistry;

import java.util.Calendar;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = UserController.BASE_URL, produces = APPLICATION_JSON_VALUE)
public class UserController {
	public static final String BASE_URL = "/users";

	@Autowired
	UserRegistry registry;

	@Autowired
	UserFinder userDictionary;

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NoSuchUserException.class})
	public ErrorDto handleNoFoundUserException(NoSuchUserException e) {
		return new ErrorDto(e.getMessage());
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public int registerUser(@RequestBody UserRegistryDto userDto) throws Exception {
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.setTime(userDto.dateOfBirth);
		return registry.registerUser(
				new User(userDto.name, userDto.email, dateOfBirth, new Address(userDto.address.country(), userDto.address.city(), userDto.address.street())));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDetailDto> getUser(@PathVariable("id") int userId) throws NoSuchUserException {
		return new ResponseEntity<>(
				new UserDetailDto(userDictionary.getUserDetails(userId)),
				HttpStatus.OK
		);
	}

	@GetMapping()
	public ResponseEntity<String> get() {
		return new ResponseEntity<>("Hello World !", HttpStatus.OK);
	}
}