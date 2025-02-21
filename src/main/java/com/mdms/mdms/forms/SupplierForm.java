package com.mdms.mdms.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierForm {

    @NotBlank(message = "Enter Supplier Name")
    private String supplierName;

    @NotBlank(message = "Enter Supplier Address")
    private String supplierAddress;

    @Email(message = "Enter Valid Email")
    @NotBlank(message = "Email is Mandatory")
    private String supplierEmail;

    @Size(min=6,max = 10,message = "Enter Valid Phone number between 6-10")
    private String supplierContactNumber;

    @Size(min=6,max = 12,message = "password Should be minmimu: 6 and maximum: 12 characters")
    private String supplierPassword;

    @Pattern(regexp = "^[0-9]{14}$", message = "Invalid FSSAI Registration Number")
    private String FSSAIRegistrationNumber;

 
}
