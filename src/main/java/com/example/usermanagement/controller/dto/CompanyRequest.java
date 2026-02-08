package com.example.usermanagement.controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {
  private String name;
  private String code;
}
