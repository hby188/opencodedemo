package com.example.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.usermanagement.unit.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {
}
