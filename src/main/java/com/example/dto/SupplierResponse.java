package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierResponse {

    private String id;
    private String name;
    private Long phone;
    private String email;
    private String address;
    private Boolean isDeleted = false;
}
