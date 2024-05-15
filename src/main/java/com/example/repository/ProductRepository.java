package com.example.repository;

import com.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Modifying
    @Transactional
    @Query("UPDATE Product e SET e.isDeleted = true WHERE e.id = :id")
    void softDeleteById(@Param("id") String productID);
}
