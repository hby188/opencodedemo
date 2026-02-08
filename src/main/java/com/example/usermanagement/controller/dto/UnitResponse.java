package com.example.usermanagement.controller.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitResponse {
  private Long id;
  private String name;
  private String code;
  private LocalDateTime createdAt;
}
