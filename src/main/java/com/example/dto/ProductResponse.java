package com.example.dto;

import com.example.model.Supplier;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ProductResponse {

    private String id;
    private String name;
    private String sku;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Boolean isDeleted = false;
    private List<SupplierResponse> suppliers;
}
