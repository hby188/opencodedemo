package com.example.usermanagement.controller.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
  @NotBlank(message = "Username is required")
  @Size(max = 50, message = "Username must not exceed 50 characters")
  private String username;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  @Size(max = 100, message = "Email must not exceed 100 characters")
  private String email;

  @Size(min = 6, message = "Password must be at least 6 characters")
  private String password;

  private Boolean enabled = true;
}
