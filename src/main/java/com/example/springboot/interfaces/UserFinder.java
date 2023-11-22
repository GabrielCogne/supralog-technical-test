package com.example.springboot.interfaces;

import com.example.springboot.entities.User;

public interface UserFinder {
    User getUserDetails(int id);
}
