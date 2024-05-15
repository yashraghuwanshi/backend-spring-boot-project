package com.example.repository;

import com.example.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SupplierRepository extends JpaRepository<Supplier, String> {
    @Modifying
    @Transactional
    @Query("UPDATE Supplier e SET e.isDeleted = true WHERE e.id = :id")
    void softDeleteById(@Param("id") String id);
}
