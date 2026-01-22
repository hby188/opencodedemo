package com.example.usermanagement.controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
  private String username;
  private String email;
}
