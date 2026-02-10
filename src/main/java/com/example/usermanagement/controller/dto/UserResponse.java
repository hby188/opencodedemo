package com.example.usermanagement.controller.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private Long id;
  private String username;
  private String email;
  private boolean enabled;
  private LocalDateTime createdAt;
}
