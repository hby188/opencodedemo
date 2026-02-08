package com.example.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.usermanagement.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
