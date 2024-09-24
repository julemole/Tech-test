package com.litethinking.Inventario.repository;

import com.litethinking.Inventario.model.Customer;
import com.litethinking.Inventario.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByCode(String code);
    void deleteByCode(String code);
    List<Product> findByCompanyNit(String companyNit);
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.categories WHERE p.code = :code")
    Optional<Product> findByCodeWithCategories(String code);
    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.categories")
    List<Product> findAllWithCategories();
}
