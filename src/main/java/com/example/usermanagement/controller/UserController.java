package com.example.usermanagement.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.model.User;
import com.example.usermanagement.service.UserService;
import com.example.usermanagement.controller.dto.UserRequest;
import com.example.usermanagement.controller.dto.UserResponse;
import com.example.usermanagement.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<UserResponse> getAll() {
    return userService.findAll().stream()
      .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getCreatedAt()))
      .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public UserResponse getById(@PathVariable Long id) {
    User u = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    return new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getCreatedAt());
  }

  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
    User user = User.builder()
      .username(request.getUsername())
      .email(request.getEmail())
      .build();
    User saved = userService.save(user);
    UserResponse response = new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getCreatedAt());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public UserResponse update(@PathVariable Long id, @RequestBody UserRequest request) {
    User toUpdate = User.builder()
      .username(request.getUsername())
      .email(request.getEmail())
      .build();
    User updated = userService.update(id, toUpdate);
    return new UserResponse(updated.getId(), updated.getUsername(), updated.getEmail(), updated.getCreatedAt());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
