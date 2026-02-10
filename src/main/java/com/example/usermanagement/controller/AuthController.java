package com.example.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.controller.dto.LoginRequest;
import com.example.usermanagement.controller.dto.LoginResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        var user = (com.example.usermanagement.model.User) authentication.getPrincipal();
        var roles = user.getAuthorities().stream()
            .map(auth -> auth.getAuthority())
            .collect(java.util.stream.Collectors.toSet());
        
        LoginResponse response = new LoginResponse("Login successful", user.getUsername(), roles);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> getCurrentUser(Authentication authentication) {
        var user = (com.example.usermanagement.model.User) authentication.getPrincipal();
        var roles = user.getAuthorities().stream()
            .map(auth -> auth.getAuthority())
            .collect(java.util.stream.Collectors.toSet());
        
        LoginResponse response = new LoginResponse("Current user info", user.getUsername(), roles);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful");
    }
}