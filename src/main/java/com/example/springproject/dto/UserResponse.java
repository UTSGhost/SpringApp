package com.example.springproject.dto;

import com.example.springproject.entity.User;

public record UserResponse(Long id, String name, String email) {
    public UserResponse(User user){
        this(user.getId(), user.getName(), user.getEmail());
    }
}
