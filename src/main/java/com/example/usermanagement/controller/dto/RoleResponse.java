package com.example.usermanagement.controller.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public RoleResponse(Long id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }
}