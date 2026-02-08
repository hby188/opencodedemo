package com.example.usermanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.usermanagement.unit.Unit;
import com.example.usermanagement.service.UnitService;
import com.example.usermanagement.controller.dto.UnitRequest;
import com.example.usermanagement.controller.dto.UnitResponse;
import com.example.usermanagement.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/units")
public class UnitController {

  private final UnitService unitService;

  @Autowired
  public UnitController(UnitService unitService) {
    this.unitService = unitService;
  }

  @GetMapping
  public List<UnitResponse> getAll() {
    return unitService.findAll().stream()
      .map(u -> new UnitResponse(u.getId(), u.getName(), u.getCode(), u.getCreatedAt()))
      .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public UnitResponse getById(@PathVariable Long id) {
    Unit u = unitService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unit not found with id " + id));
    return new UnitResponse(u.getId(), u.getName(), u.getCode(), u.getCreatedAt());
  }

  @PostMapping
  public ResponseEntity<UnitResponse> create(@RequestBody UnitRequest request) {
    Unit unit = new Unit();
    unit.setName(request.getName());
    unit.setCode(request.getCode());
    Unit saved = unitService.save(unit);
    UnitResponse response = new UnitResponse(saved.getId(), saved.getName(), saved.getCode(), saved.getCreatedAt());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public UnitResponse update(@PathVariable Long id, @RequestBody UnitRequest request) {
    Unit toUpdate = new Unit();
    toUpdate.setName(request.getName());
    toUpdate.setCode(request.getCode());
    Unit updated = unitService.update(id, toUpdate);
    return new UnitResponse(updated.getId(), updated.getName(), updated.getCode(), updated.getCreatedAt());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    unitService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
