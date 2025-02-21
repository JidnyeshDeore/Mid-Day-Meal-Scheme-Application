package com.mdms.mdms.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SchoolForm {

    @NotBlank(message = "school Name is Required")
    @Size(min=5,message = "minimum 5 character is required")
    private String school_Name;

    @NotBlank(message = "school Address is Required")
    private String school_Address;

    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is Required")
    private String school_Email;

    @Size(min=6,max = 10,message = "Enter Valid Phone number between 6-10")
    private String school_Phone_Number;

    @Size(min=6,max = 12,message = "password Should be minmimu: 6 and maximum: 12 characters")
    private String school_Password;

    @NotBlank(message = "Enter School MDM Instructor")
    private String school_mdm_Instructor;
    
    private int school_Strength;

}
