package com.example.usermanagement.service;

import java.util.List;
import java.util.Optional;
import com.example.usermanagement.model.User;

public interface UserService {
  List<User> findAll();
  Optional<User> findById(Long id);
  User save(User user);
  User update(Long id, User user);
  void delete(Long id);
}
