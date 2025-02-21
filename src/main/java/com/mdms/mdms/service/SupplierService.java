package com.mdms.mdms.service;

import java.util.List;

import com.mdms.mdms.dto.MealRequestDTO;
import com.mdms.mdms.dto.SupplierDetailsDTO;
import com.mdms.mdms.entites.Supplier;



public interface SupplierService {

    public Supplier saveSupplier(Supplier supplier);

    public List<Supplier> getAllSupplier();

    public Supplier getSupplierById(String supplierId);

    public List<Supplier> getActiveSuppliers();

    public Supplier findBySupplierEmail(String email);

    public List<MealRequestDTO> getMealRequestsForSupplier(Supplier supplier);

    public SupplierDetailsDTO getSupplierDetails(String supplierId);

}
