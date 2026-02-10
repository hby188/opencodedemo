package com.example.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.usermanagement.model.User;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final com.example.usermanagement.service.RoleService roleService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, com.example.usermanagement.service.RoleService roleService) {
    this.userRepository = userRepository;
    this.roleService = roleService;
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User save(User user) {
    if (user.getId() == null) {
      if (existsByUsername(user.getUsername())) {
        throw new IllegalArgumentException("Username '" + user.getUsername() + "' already exists");
      }
      if (existsByEmail(user.getEmail())) {
        throw new IllegalArgumentException("Email '" + user.getEmail() + "' already exists");
      }
    }
    return userRepository.save(user);
  }

  @Override
  public User update(Long id, User user) {
    User existing = userRepository.findById(id)
        .orElseThrow(() -> new com.example.usermanagement.exception.ResourceNotFoundException("User not found with id " + id));
    
    if (!existing.getUsername().equals(user.getUsername()) && existsByUsername(user.getUsername())) {
      throw new IllegalArgumentException("Username '" + user.getUsername() + "' already exists");
    }
    if (!existing.getEmail().equals(user.getEmail()) && existsByEmail(user.getEmail())) {
      throw new IllegalArgumentException("Email '" + user.getEmail() + "' already exists");
    }
    
    existing.setUsername(user.getUsername());
    existing.setEmail(user.getEmail());
    existing.setEnabled(user.isEnabled());
    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
      existing.setPassword(user.getPassword());
    }
    return userRepository.save(existing);
  }

  @Override
  public void delete(Long id) {
    if (!userRepository.existsById(id)) {
      throw new com.example.usermanagement.exception.ResourceNotFoundException("User not found with id " + id);
    }
    userRepository.deleteById(id);
  }

  @Override
  public User assignRoles(Long userId, Set<Role> roles) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new com.example.usermanagement.exception.ResourceNotFoundException("User not found with id " + userId));
    user.getRoles().addAll(roles);
    return userRepository.save(user);
  }

  @Override
  public User removeRoles(Long userId, Set<Role> roles) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new com.example.usermanagement.exception.ResourceNotFoundException("User not found with id " + userId));
    user.getRoles().removeAll(roles);
    return userRepository.save(user);
  }

  @Override
  public User updateUserRoles(Long userId, Set<Role> roles) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new com.example.usermanagement.exception.ResourceNotFoundException("User not found with id " + userId));
    user.setRoles(roles);
    return userRepository.save(user);
  }

  @Override
  public Set<Role> getUserRoles(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new com.example.usermanagement.exception.ResourceNotFoundException("User not found with id " + userId));
    return user.getRoles();
  }

  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
