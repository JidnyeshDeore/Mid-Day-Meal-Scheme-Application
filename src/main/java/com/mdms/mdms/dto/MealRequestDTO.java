package com.mdms.mdms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class MealRequestDTO {

    private String mealRequestId;
    private String schoolName;
    private LocalDate deliveryDate;
    private String nutritionalRequirements;
    private List<MealItemDTO> mealItems;
}
