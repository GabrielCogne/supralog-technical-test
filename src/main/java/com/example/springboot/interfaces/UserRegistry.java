package com.example.springboot.interfaces;

import com.example.springboot.entities.User;

public interface UserRegistry {
    int registerUser(User user) throws Exception;
}
