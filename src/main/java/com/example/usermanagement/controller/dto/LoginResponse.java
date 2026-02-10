package com.example.usermanagement.controller.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String message;
    private String username;
    private java.util.Set<String> roles;
    
    public LoginResponse(String message, String username, java.util.Set<String> roles) {
        this.message = message;
        this.username = username;
        this.roles = roles;
    }
}