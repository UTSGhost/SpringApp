package com.example.springproject.dto;

import com.example.springproject.controller.AuthController;

public record AuthResponse (boolean success, String message, String token){
}
