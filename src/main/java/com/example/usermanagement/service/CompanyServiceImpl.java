package com.example.usermanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usermanagement.company.Company;
import com.example.usermanagement.repository.CompanyRepository;
import com.example.usermanagement.exception.ResourceNotFoundException;

@Service
public class CompanyServiceImpl implements CompanyService {
  private final CompanyRepository companyRepository;

  @Autowired
  public CompanyServiceImpl(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  @Override
  public List<Company> findAll() {
    return companyRepository.findAll();
  }

  @Override
  public Optional<Company> findById(Long id) {
    return companyRepository.findById(id);
  }

  @Override
  public Company save(Company company) {
    return companyRepository.save(company);
  }

  @Override
  public Company update(Long id, Company company) {
    Company existing = companyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
    existing.setName(company.getName());
    existing.setCode(company.getCode());
    return companyRepository.save(existing);
  }

  @Override
  public void delete(Long id) {
    if (!companyRepository.existsById(id)) {
      throw new ResourceNotFoundException("Company not found with id " + id);
    }
    companyRepository.deleteById(id);
  }
}
