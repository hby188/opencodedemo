package com.example.usermanagement.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.Set;

@Data
public class UserRoleRequest {
    @NotEmpty(message = "Roles cannot be empty")
    private Set<Long> roleIds;
}