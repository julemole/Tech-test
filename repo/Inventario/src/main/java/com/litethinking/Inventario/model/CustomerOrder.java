package com.litethinking.Inventario.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "customer_order")
@Data
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private Date orderDate;

    @Basic
    private Double total;

    @ManyToOne
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_code")
    )
    private Set<Product> products;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;

    public CustomerOrder(Date orderDate, Customer customer) {
        this.orderDate = orderDate;
        this.customer = customer;
    }

    public CustomerOrder() { }
}
