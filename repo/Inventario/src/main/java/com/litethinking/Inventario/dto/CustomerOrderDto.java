package com.litethinking.Inventario.dto;

import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDto {
    private Long id;
    private Date orderDate;
    private Double total;
    private Long customerId;
    private Set<String> productCodes;
}
