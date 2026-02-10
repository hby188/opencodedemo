package com.example.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.example.usermanagement.model.User;
import com.example.usermanagement.model.Role;

public interface UserService {
  List<User> findAll();
  Optional<User> findById(Long id);
  Optional<User> findByUsername(String username);
  User save(User user);
  User update(Long id, User user);
  void delete(Long id);
  
  User assignRoles(Long userId, Set<Role> roles);
  User removeRoles(Long userId, Set<Role> roles);
  User updateUserRoles(Long userId, Set<Role> roles);
  Set<Role> getUserRoles(Long userId);
  
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
}
