package com.litethinking.Inventario.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_product")
@Data
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private Integer quantity;

    @ManyToOne
    private CustomerOrder order;

    @ManyToOne
    private Product product;
}
