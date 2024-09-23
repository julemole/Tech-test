package com.litethinking.Inventario.dto;

import lombok.Data;

@Data
public class CompanyDto {
    private String NIT; // NIT como PK
    private String name;
    private String address;
    private String phone;
}
