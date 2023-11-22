package com.example.springboot.controllers.dto;

import com.example.springboot.entities.User;

public class UserDetailDto {
    private final String name;

    public UserDetailDto(User user) {
        this.name = user.getName();
    }

    public String getName() {
        return name;
    }
}
