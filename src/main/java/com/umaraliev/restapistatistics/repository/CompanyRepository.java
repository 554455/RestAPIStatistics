package com.umaraliev.restapistatistics.repository;

import com.umaraliev.restapistatistics.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
