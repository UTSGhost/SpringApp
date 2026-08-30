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
        userService.registerUser(
                newUser.getName(),
                newUser.getEmail(),
                newUser.getPassword()
        );
        // generates token so registration also logs in user
        String token = jwtService.generateToken(newUser.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest){
        // will throw exception if error
        userService.loginUser(loginRequest.email(), loginRequest.password());
        String token = jwtService.generateToken(loginRequest.email());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
