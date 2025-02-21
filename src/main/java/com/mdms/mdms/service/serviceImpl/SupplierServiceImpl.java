package com.mdms.mdms.service.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdms.mdms.dto.MealItemDTO;
import com.mdms.mdms.dto.MealRequestDTO;
import com.mdms.mdms.dto.SupplierDetailsDTO;
import com.mdms.mdms.entites.Supplier;
import com.mdms.mdms.repositories.SupplierRepository;
import com.mdms.mdms.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService{

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Supplier saveSupplier(Supplier supplier) {
        
        System.out.println("SupplierServiceImpl:-->Inside saveSupplier method: ");
        String supplierId=UUID.randomUUID().toString();
        supplier.setSupplierId(supplierId);

        supplier.setSupplierPassword(passwordEncoder.encode(supplier.getPassword()));

        return supplierRepository.save(supplier);

    }


    @Override
    public List<Supplier> getAllSupplier() {
        
        return supplierRepository.findAll();
    }


    @Override
    public Supplier getSupplierById(String supplierId) {
        
        return supplierRepository.findById(supplierId).orElse(null);
    }


    @Override
    public List<Supplier> getActiveSuppliers() {
        // TODO Auto-generated method stub
        return supplierRepository.findByIsActiveTrue();
    }


    @Override
    public Supplier findBySupplierEmail(String email) {
        
       return supplierRepository.findBySupplierEmail(email).orElse(null);
    }


    @Override
    public List<MealRequestDTO> getMealRequestsForSupplier(Supplier newSupplier) {
        String supplierId=newSupplier.getSupplierId();
        Supplier supplier = supplierRepository.findSupplierWithMealRequestsAndItems(supplierId)
                                               .orElseThrow(null);
        
        if (supplier.getMealRequests().isEmpty()) {
            return Collections.emptyList();
        }
        
        return supplier.getMealRequests().stream()
                       .map(request -> {
                           MealRequestDTO dto = new MealRequestDTO();
                           dto.setMealRequestId(request.getMealRequestId());
                           dto.setSchoolName(request.getSchool().getSchool_Name());
                           dto.setDeliveryDate(request.getDeliveryDate());
                           dto.setNutritionalRequirements(request.getNutritionalRequirements());
                           dto.setMealItems(request.getMealItems().stream()
                                                    .map(item -> {
                                                        MealItemDTO itemDTO = new MealItemDTO();
                                                        itemDTO.setName(item.getName());
                                                        itemDTO.setQuantity(item.getQuantity());
                                                        return itemDTO;
                                                    })
                                                    .collect(Collectors.toList()));
                           return dto;
                       }).collect(Collectors.toList());
    }
    


    @Override
    public SupplierDetailsDTO getSupplierDetails(String supplierId) {
        Supplier supplier = supplierRepository.findSupplierById(supplierId)
                                            .orElseThrow(() -> new RuntimeException("Supplier not found"));

        SupplierDetailsDTO dto = new SupplierDetailsDTO();
        dto.setSupplierId(supplier.getSupplierId());
        dto.setSupplierName(supplier.getSupplierName());

        // Fetch meal requests and map to DTO
        List<MealRequestDTO> mealRequests = supplier.getMealRequests().stream()
            .map(request -> {
                MealRequestDTO requestDTO = new MealRequestDTO();
                requestDTO.setMealRequestId(request.getMealRequestId());
                requestDTO.setSchoolName(request.getSchool().getSchool_Name());
                requestDTO.setDeliveryDate(request.getDeliveryDate());
                requestDTO.setNutritionalRequirements(request.getNutritionalRequirements());
                
                // Fetch meal items and map to DTO
                List<MealItemDTO> mealItems = request.getMealItems().stream()
                    .map(item -> {
                        MealItemDTO itemDTO = new MealItemDTO();
                        itemDTO.setName(item.getName());
                        itemDTO.setQuantity(item.getQuantity());
                        return itemDTO;
                    })
                    .collect(Collectors.toList());
                requestDTO.setMealItems(mealItems);
                return requestDTO;
            })
            .collect(Collectors.toList());

        dto.setMealRequests(mealRequests);
        return dto;
    }



}
