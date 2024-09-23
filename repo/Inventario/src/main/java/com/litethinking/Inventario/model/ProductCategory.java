package com.litethinking.Inventario.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "product_category",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_code", "category_id"})
)
@Data
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Category category;

    public ProductCategory() {}

    public ProductCategory(Product product, Category category) {
        this.product = product;
        this.category = category;
    }
}
