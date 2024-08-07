package com.example.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "PRODUCT_TBL")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String sku;
    private String description;
    private BigDecimal price;
    private Integer quantity;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @OneToMany(targetEntity = Supplier.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Supplier> suppliers = new ArrayList<>();
}
