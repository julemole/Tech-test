package com.litethinking.Inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String code; // code como PK
    private String name;
    private String description;
    private Double price;
    private String companyNit;
    private Set<Long> categoryIds;
    private Boolean isInInventory;
}
