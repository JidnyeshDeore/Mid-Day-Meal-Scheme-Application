package com.mdms.mdms.forms;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class MealItem {

    @NotBlank(message = "Item name is required")
    private String name;

    @NotNull(message = "Quantity is required")
    private Double quantity;
}   
