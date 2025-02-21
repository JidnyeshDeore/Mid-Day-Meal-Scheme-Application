package com.mdms.mdms.forms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MealRequestForm {

//    @NotNull(message = "Enter quantity")
//     private Map<String,String> mealItems = new HashMap<>();// e.g., "Rice, Dal, Vegetables"


    @NotNull(message = "Meal items cannot be null")
    private List<MealItem> mealItems = new ArrayList<>();

    @NotNull
    private String nutritionalRequirements; // E.g., "Vegetarian, Fortified"

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate; // Date the meal needs to be delivered

    @NotNull(message="Enter the name")
    private String contactPerson; // Contact person at the school

    @Size(min = 10,max=10,message = "Size should be 10")
    private String contactPhone; // Contact phone of the person

}
