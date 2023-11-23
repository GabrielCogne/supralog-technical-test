package com.example.springboot.interfaces;

import com.example.springboot.entities.User;
import com.example.springboot.exceptions.NoSuchUserException;

import java.math.BigInteger;

public interface UserFinder {
    /**
     * Return user details
     * @param id The id of the user
     * @throws NoSuchUserException The user is not saved in the database
     */
    User getUserDetails(BigInteger id) throws NoSuchUserException;
}
