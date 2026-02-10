package com.example.usermanagement.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.model.User;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.service.UserService;
import com.example.usermanagement.service.RoleService;
import com.example.usermanagement.controller.dto.*;
import com.example.usermanagement.exception.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final RoleService roleService;

  @Autowired
  public UserController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping
  public List<UserResponse> getAll() {
    return userService.findAll().stream()
      .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.isEnabled(), u.getCreatedAt()))
      .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public UserResponse getById(@PathVariable Long id) {
    User u = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    return new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.isEnabled(), u.getCreatedAt());
  }

  @GetMapping("/{id}/roles")
  public UserWithRolesResponse getUserWithRoles(@PathVariable Long id) {
    User u = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    Set<RoleResponse> roleResponses = u.getRoles().stream()
      .map(r -> new RoleResponse(r.getId(), r.getName(), r.getDescription(), r.getCreatedAt()))
      .collect(Collectors.toSet());
    return new UserWithRolesResponse(u.getId(), u.getUsername(), u.getEmail(), u.isEnabled(), u.getCreatedAt(), roleResponses);
  }

  @PostMapping
  public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
    User user = User.builder()
      .username(request.getUsername())
      .email(request.getEmail())
      .password(request.getPassword())
      .enabled(request.getEnabled() != null ? request.getEnabled() : true)
      .build();
    User saved = userService.save(user);
    UserResponse response = new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail(), saved.isEnabled(), saved.getCreatedAt());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
    User toUpdate = User.builder()
      .username(request.getUsername())
      .email(request.getEmail())
      .password(request.getPassword())
      .enabled(request.getEnabled())
      .build();
    User updated = userService.update(id, toUpdate);
    return new UserResponse(updated.getId(), updated.getUsername(), updated.getEmail(), updated.isEnabled(), updated.getCreatedAt());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/roles")
  public ResponseEntity<UserWithRolesResponse> assignRoles(@PathVariable Long id, @Valid @RequestBody UserRoleRequest request) {
    User user = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    
    Set<Role> roles = request.getRoleIds().stream()
      .map(roleId -> roleService.findById(roleId)
        .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId)))
      .collect(Collectors.toSet());
    
    User updated = userService.assignRoles(id, roles);
    Set<RoleResponse> roleResponses = updated.getRoles().stream()
      .map(r -> new RoleResponse(r.getId(), r.getName(), r.getDescription(), r.getCreatedAt()))
      .collect(Collectors.toSet());
    
    UserWithRolesResponse response = new UserWithRolesResponse(updated.getId(), updated.getUsername(), updated.getEmail(), updated.isEnabled(), updated.getCreatedAt(), roleResponses);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/roles")
  public ResponseEntity<UserWithRolesResponse> updateUserRoles(@PathVariable Long id, @Valid @RequestBody UserRoleRequest request) {
    User user = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    
    Set<Role> roles = request.getRoleIds().stream()
      .map(roleId -> roleService.findById(roleId)
        .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId)))
      .collect(Collectors.toSet());
    
    User updated = userService.updateUserRoles(id, roles);
    Set<RoleResponse> roleResponses = updated.getRoles().stream()
      .map(r -> new RoleResponse(r.getId(), r.getName(), r.getDescription(), r.getCreatedAt()))
      .collect(Collectors.toSet());
    
    UserWithRolesResponse response = new UserWithRolesResponse(updated.getId(), updated.getUsername(), updated.getEmail(), updated.isEnabled(), updated.getCreatedAt(), roleResponses);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}/roles")
  public ResponseEntity<UserWithRolesResponse> removeRoles(@PathVariable Long id, @Valid @RequestBody UserRoleRequest request) {
    User user = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    
    Set<Role> roles = request.getRoleIds().stream()
      .map(roleId -> roleService.findById(roleId)
        .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId)))
      .collect(Collectors.toSet());
    
    User updated = userService.removeRoles(id, roles);
    Set<RoleResponse> roleResponses = updated.getRoles().stream()
      .map(r -> new RoleResponse(r.getId(), r.getName(), r.getDescription(), r.getCreatedAt()))
      .collect(Collectors.toSet());
    
    UserWithRolesResponse response = new UserWithRolesResponse(updated.getId(), updated.getUsername(), updated.getEmail(), updated.isEnabled(), updated.getCreatedAt(), roleResponses);
    return ResponseEntity.ok(response);
  }
}
