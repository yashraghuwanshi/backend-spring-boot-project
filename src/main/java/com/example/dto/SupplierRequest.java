package com.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierRequest {

    private String id;
    private String name;
    private Long phone;
    private String email;
    private String address;
    @JsonIgnore
    private Boolean isDeleted = false;
}
