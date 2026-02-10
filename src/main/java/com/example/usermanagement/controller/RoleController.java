package com.example.usermanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.model.Role;
import com.example.usermanagement.service.RoleService;
import com.example.usermanagement.controller.dto.RoleRequest;
import com.example.usermanagement.controller.dto.RoleResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleResponse> getAll() {
        return roleService.findAll().stream()
            .map(r -> new RoleResponse(r.getId(), r.getName(), r.getDescription(), r.getCreatedAt()))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RoleResponse getById(@PathVariable Long id) {
        Role r = roleService.findById(id)
            .orElseThrow(() -> new com.example.usermanagement.exception.ResourceNotFoundException("Role not found with id " + id));
        return new RoleResponse(r.getId(), r.getName(), r.getDescription(), r.getCreatedAt());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleResponse> getByName(@PathVariable String name) {
        return roleService.findByName(name)
            .map(r -> ResponseEntity.ok(new RoleResponse(r.getId(), r.getName(), r.getDescription(), r.getCreatedAt())))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@Valid @RequestBody RoleRequest request) {
        Role role = Role.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build();
        Role saved = roleService.save(role);
        RoleResponse response = new RoleResponse(saved.getId(), saved.getName(), saved.getDescription(), saved.getCreatedAt());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public RoleResponse update(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        Role toUpdate = Role.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build();
        Role updated = roleService.update(id, toUpdate);
        return new RoleResponse(updated.getId(), updated.getName(), updated.getDescription(), updated.getCreatedAt());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}