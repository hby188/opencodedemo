package com.example.usermanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usermanagement.unit.Unit;
import com.example.usermanagement.repository.UnitRepository;
import static java.util.Optional.*;
import com.example.usermanagement.exception.ResourceNotFoundException;

@Service
public class UnitServiceImpl implements UnitService {
  private final UnitRepository unitRepository;

  @Autowired
  public UnitServiceImpl(UnitRepository unitRepository) {
    this.unitRepository = unitRepository;
  }

  @Override
  public List<Unit> findAll() {
    return unitRepository.findAll();
  }

  @Override
  public Optional<Unit> findById(Long id) {
    return unitRepository.findById(id);
  }

  @Override
  public Unit save(Unit unit) {
    return unitRepository.save(unit);
  }

  @Override
  public Unit update(Long id, Unit unit) {
    Unit existing = unitRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Unit not found with id " + id));
    existing.setName(unit.getName());
    existing.setCode(unit.getCode());
    return unitRepository.save(existing);
  }

  @Override
  public void delete(Long id) {
    if (!unitRepository.existsById(id)) {
      throw new ResourceNotFoundException("Unit not found with id " + id);
    }
    unitRepository.deleteById(id);
  }
}
