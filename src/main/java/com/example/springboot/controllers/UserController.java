package com.example.springboot.controllers;

import com.example.springboot.controllers.dto.ErrorDto;
import com.example.springboot.entities.Address;
import com.example.springboot.exceptions.NoSuchUserException;
import com.example.springboot.exceptions.UnauthorizedUserCreationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.controllers.dto.UserRegistryDto;
import com.example.springboot.entities.User;
import com.example.springboot.interfaces.UserFinder;
import com.example.springboot.interfaces.UserRegistry;

import java.util.Calendar;
import java.util.stream.Collectors;

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

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ErrorDto handleValidationError(MethodArgumentNotValidException e) {
		return new ErrorDto("Validation errors: " +
				e.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", ")));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({UnauthorizedUserCreationException.class})
	public ErrorDto handleUserRegistryException(UnauthorizedUserCreationException e) {
		return new ErrorDto(e.getMessage());
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> registerUser(@RequestParam(value = "unfold", required = false) boolean unfold, @Valid @RequestBody UserRegistryDto userDto) throws Exception {
		int id = registry.registerUser(userDto.toObject());
		if (unfold) {
			return new ResponseEntity<>(userDictionary.getUserDetails(id), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") int userId) throws NoSuchUserException {
		return new ResponseEntity<>(
				userDictionary.getUserDetails(userId),
				HttpStatus.OK
		);
	}

	@GetMapping()
	public ResponseEntity<String> get() {
		return new ResponseEntity<>("Hello World !", HttpStatus.OK);
	}
}
