package com.example.usermanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usermanagement.model.User;
import com.example.usermanagement.repository.UserRepository;

import static java.util.Optional.*;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
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
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public User update(Long id, User user) {
    User existing = userRepository.findById(id)
        .orElseThrow(() -> new com.example.usermanagement.exception.ResourceNotFoundException("User not found with id " + id));
    existing.setUsername(user.getUsername());
    existing.setEmail(user.getEmail());
    return userRepository.save(existing);
  }

  @Override
  public void delete(Long id) {
    if (!userRepository.existsById(id)) {
      throw new com.example.usermanagement.exception.ResourceNotFoundException("User not found with id " + id);
    }
    userRepository.deleteById(id);
  }
}
