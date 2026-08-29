package com.example.springproject.controller;

import com.example.springproject.entity.User;
import com.example.springproject.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public String newUser(@RequestBody User newUser){
        userService.registerUser(newUser.getName(), newUser.getEmail(), newUser.getPassword());
        return "Successfully registrated!";
    }
}
