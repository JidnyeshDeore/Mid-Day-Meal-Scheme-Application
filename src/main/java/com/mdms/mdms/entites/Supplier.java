package com.mdms.mdms.entites;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "supplier_table")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "supplierId")
public class Supplier implements UserDetails{

    @Id
    private String supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierEmail;
    private String supplierContactNumber;
    private String supplierPassword;
    private String FSSAIRegistrationNumber;
  

    @OneToMany(mappedBy = "assignedSupplier", fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private List<MealRequest> mealRequests; // List of meal requests assigned to this supplier

    
    private boolean isActive=true; 


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


    @Override
    public String getPassword() {
        
        return this.supplierPassword;
    }


    @Override
    public String getUsername() {
        return this.supplierEmail;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierAddress='" + supplierAddress + '\'' +
                ", supplierEmail='" + supplierEmail + '\'' +
                ", supplierContactNumber='" + supplierContactNumber + '\'' +
                ", FSSAIRegistrationNumber='" + FSSAIRegistrationNumber + '\'' +
                '}';
    }

}
