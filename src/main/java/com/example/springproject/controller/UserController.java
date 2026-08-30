package com.example.springproject.controller;

import com.example.springproject.dto.UserResponse;
import com.example.springproject.entity.User;
import com.example.springproject.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    // unpacks the user from SecurityContext
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserResponse(user));
    }
}
