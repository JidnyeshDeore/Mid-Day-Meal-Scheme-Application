package com.mdms.mdms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mdms.mdms.entites.Supplier;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String>{

    @Query("SELECT s FROM Supplier s WHERE s.supplierEmail = :supplierEmail")
    Optional< Supplier> findBySupplierEmail(String supplierEmail);


    @Query("SELECT s FROM Supplier s WHERE s.isActive = true")
    List<Supplier> findByIsActiveTrue();

    // Optional<Supplier> findByEmail(String email);

    @Query("SELECT s FROM Supplier s JOIN FETCH s.mealRequests mr JOIN FETCH mr.mealItems WHERE s.supplierId = :supplierId")
    Optional<Supplier> findSupplierWithMealRequestsAndItems(@Param("supplierId") String supplierId);
    

    @Query("SELECT s FROM Supplier s WHERE s.supplierId = :supplierId")
    Optional<Supplier> findSupplierById(@Param("supplierId") String supplierId);

}
