package com.litethinking.Inventario.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    private String name;
    @Basic
    private String username;
    @Basic
    private String email;
    @Basic
    private String password;
    @Basic
    private String role;

}
