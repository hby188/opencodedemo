package com.example.usermanagement.service;

import java.util.List;
import java.util.Optional;
import com.example.usermanagement.unit.Unit;

public interface UnitService {
  List<Unit> findAll();
  Optional<Unit> findById(Long id);
  Unit save(Unit unit);
  Unit update(Long id, Unit unit);
  void delete(Long id);
}
