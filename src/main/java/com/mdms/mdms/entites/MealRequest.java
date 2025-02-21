package com.mdms.mdms.entites;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mdms.mdms.forms.MealItem;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "mealRequestId")
public class MealRequest {


    @Id
    private String mealRequestId;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ElementCollection
    @CollectionTable(name = "meal_items", joinColumns = @JoinColumn(name = "meal_request_id"))
    @BatchSize(size = 10)
    private List<MealItem> mealItems = new ArrayList<>();

    // private Double quantity; // Quantity needed (e.g., 50 kg)
    private String nutritionalRequirements; // E.g., "Vegetarian, Fortified"
    private LocalDate deliveryDate; // Date the meal needs to be delivered
    private String contactPerson; // Contact person at the school
    private String contactPhone; // Contact phone of the person


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplierId")
    private Supplier assignedSupplier;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    public enum Status {
        PENDING, APPROVED, ASSIGNED, DELIVERED
    }

    @Override
    public String toString() {
        return "MealRequest{" +
                "mealRequestId='" + mealRequestId + '\'' +
                ", schoolId='" + (school != null ? school.getSchool_Id() : "N/A") + '\'' +
                ", mealItems=" + mealItems +
                ", nutritionalRequirements='" + nutritionalRequirements + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                '}';
    }

}
