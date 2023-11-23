package com.example.springboot.interfaces;

import com.example.springboot.entities.User;

import java.math.BigInteger;

public interface UserFinder {
    User getUserDetails(BigInteger id);
}
