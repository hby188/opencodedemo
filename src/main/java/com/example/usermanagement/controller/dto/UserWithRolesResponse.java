package com.example.usermanagement.controller.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserWithRolesResponse {
    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private LocalDateTime createdAt;
    private Set<RoleResponse> roles;

    public UserWithRolesResponse(Long id, String username, String email, boolean enabled, LocalDateTime createdAt, Set<RoleResponse> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.roles = roles;
    }
}