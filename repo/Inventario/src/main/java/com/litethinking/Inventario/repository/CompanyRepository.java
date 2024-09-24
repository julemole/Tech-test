package com.litethinking.Inventario.repository;

import com.litethinking.Inventario.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("companyRepository")
public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<Company> findByNit(String NIT);
    void deleteByNit(String NIT);
}
