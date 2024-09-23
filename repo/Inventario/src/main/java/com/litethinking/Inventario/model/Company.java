package com.litethinking.Inventario.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Entity
@Table(name = "company")
@Data
public class Company {
    @Id
    private String nit; // NIT como PK

    @Basic
    private String name;
    @Basic
    private String address;
    @Basic
    private String phone;

    public Company(String nit, String name, String address, String phone) {
        this.nit = nit;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Company() { }
}

