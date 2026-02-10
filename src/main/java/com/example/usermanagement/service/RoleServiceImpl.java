package com.example.usermanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usermanagement.model.Role;
import com.example.usermanagement.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new IllegalArgumentException("Role with name '" + role.getName() + "' already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role update(Long id, Role role) {
        Role existing = roleRepository.findById(id)
            .orElseThrow(() -> new com.example.usermanagement.exception.ResourceNotFoundException("Role not found with id " + id));
        
        if (!existing.getName().equals(role.getName()) && roleRepository.existsByName(role.getName())) {
            throw new IllegalArgumentException("Role with name '" + role.getName() + "' already exists");
        }
        
        existing.setName(role.getName());
        existing.setDescription(role.getDescription());
        return roleRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new com.example.usermanagement.exception.ResourceNotFoundException("Role not found with id " + id);
        }
        roleRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}