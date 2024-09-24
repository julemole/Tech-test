package com.litethinking.Inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private String NIT; // NIT como PK
    private String name;
    private String address;
    private String phone;
}
