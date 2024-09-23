package com.litethinking.Inventario.model;
import lombok.Data;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private String name;

    @Basic
    private String email;
    @Basic
    private String address;
    @Basic
    private String phone;


    public Customer(Long id, String name, String email, String address, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Customer() { }

}
