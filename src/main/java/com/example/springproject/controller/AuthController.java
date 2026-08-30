package com.example.springproject.controller;

import com.example.springproject.dto.AuthResponse;
import com.example.springproject.dto.LoginRequest;
import com.example.springproject.entity.User;
import com.example.springproject.service.JwtService;
import com.example.springproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User newUser){
        boolean isSuccess = userService.registerUser(
                newUser.getName(),
                newUser.getEmail(),
                newUser.getPassword()
        );
        if (isSuccess){
            AuthResponse response = new AuthResponse(
                    true,
                    "User successfully registered",
                    jwtService.generateToken(newUser.getEmail()) // user is auto logged in after registration
            );
            return ResponseEntity.ok(response);
        } else {
            AuthResponse response = new AuthResponse(
                    false,
                    "This Email is already registered",
                    null
            );
            return ResponseEntity.status(400).body(response);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest){
        boolean isSuccess = userService.loginUser(
                loginRequest.email(),
                loginRequest.password()
        );
        if (isSuccess){
            AuthResponse response = new AuthResponse(
                    true,
                    "Successfully logged in",
                    jwtService.generateToken(loginRequest.email())
            );
            return ResponseEntity.ok(response);
        } else {
            AuthResponse response = new AuthResponse(
                    false,
                    "Password or Email is incorrect",
                    null
            );
            return ResponseEntity.status(401).body(response);
        }
    }
}
