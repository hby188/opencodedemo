package com.example.usermanagement.service;

import java.util.List;
import java.util.Optional;
import com.example.usermanagement.company.Company;

public interface CompanyService {
  List<Company> findAll();
  Optional<Company> findById(Long id);
  Company save(Company company);
  Company update(Long id, Company company);
  void delete(Long id);
}
