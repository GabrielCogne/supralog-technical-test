package com.example.springboot.interfaces;

import com.example.springboot.entities.User;
import com.example.springboot.exceptions.UnauthorizedUserCreationException;

public interface UserRegistry {
    /**
     * Register a user
     * @param user The user information
     * @param unfold Should the return value be the user or only his id
     * @throws UnauthorizedUserCreationException The user is not old enough or doesn't live in France
     */
    Object registerUser(User user, boolean unfold) throws UnauthorizedUserCreationException;
}
