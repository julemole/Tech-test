package com.litethinking.Inventario.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class CustomerOrderDto {
    private Long id;
    private Date orderDate;
    private Double total;
    private Long customerId;
    private Set<String> productCodes;
}
