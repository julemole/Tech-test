package com.litethinking.Inventario.repository;

import com.litethinking.Inventario.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productCategoryRepository")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
}
