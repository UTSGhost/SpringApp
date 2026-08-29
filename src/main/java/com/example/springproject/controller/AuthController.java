package com.example.springproject.controller;

import com.example.springproject.dto.AuthResponse;
import com.example.springproject.dto.LoginRequest;
import com.example.springproject.entity.User;
import com.example.springproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User newUser){
        boolean isSuccess = userService.registerUser(
                newUser.getName(),
                newUser.getEmail(),
                newUser.getPassword()
        );
        if (isSuccess){
            AuthResponse response = new AuthResponse(true, "User successfully registered");
            return ResponseEntity.ok(response);
        } else {
            AuthResponse response = new AuthResponse(false, "This Email is already registered");
            return ResponseEntity.status(400).body(response);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest){
        boolean isSuccess = userService.loginUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        if (isSuccess){
            AuthResponse response = new AuthResponse(true, "Successfully logged in");
            return ResponseEntity.ok(response);
        } else {
            AuthResponse response = new AuthResponse(false, "Password or Email is incorrect");
            return ResponseEntity.status(401).body(response);
        }
    }
}
