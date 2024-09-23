package com.litethinking.Inventario.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    private String code; // Primary key

    @Basic
    private String name;
    @Basic
    private String description;
    @Basic
    private Double price;

    @Basic
    private Boolean isInInventory;

    @ManyToOne
    @JoinColumn(name = "Company_Nit", referencedColumnName = "Nit")
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_code"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToMany(mappedBy = "products")
    private Set<CustomerOrder> orders;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;

    public Product(String code, String name, String description, Double price, Company company, Set<Category> categories, Boolean isInInventory) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.company = company;
        this.categories = categories;
        this.isInInventory = isInInventory;
    }

    public Product() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
