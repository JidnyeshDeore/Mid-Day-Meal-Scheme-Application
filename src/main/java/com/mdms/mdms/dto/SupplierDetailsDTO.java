package com.mdms.mdms.dto;

import java.util.List;

import lombok.Data;

@Data
public class SupplierDetailsDTO {


    private String supplierId;
    private String supplierName;
    private List<MealRequestDTO> mealRequests;
}
