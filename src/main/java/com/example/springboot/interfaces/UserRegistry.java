package com.example.springboot.interfaces;

import com.example.springboot.entities.User;

import java.math.BigInteger;

public interface UserRegistry {
    Object registerUser(User user, boolean unfold) throws Exception;
}
