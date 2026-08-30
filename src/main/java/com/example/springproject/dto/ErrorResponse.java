package com.example.springproject.dto;

public record ErrorResponse(int status, String error, String message, long timestamp) {
}
