package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequest {

    private String name;
    private String sku;
    private String description;
    private BigDecimal price;
    private Integer quantity;

    private List<SupplierRequest> suppliers;


}
