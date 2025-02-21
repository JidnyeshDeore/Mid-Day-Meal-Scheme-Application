package com.mdms.mdms.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class StudentForm {

    @NotBlank(message = "Enter Student Name...")
    private String student_Name;

    @Email(message = "Invalid Email..")
    @NotBlank(message = "Email is required..")
    private String student_Email;

    @Size(min = 4,max = 10,message = "Password Should be between 4 to 10 characters")
    private String student_Password;

    @NotBlank(message = "Provide Complete Student Address")
    private String student_Address;

    @Size(min = 10,max = 10,message = "Enter 10 digit phone number")
    private String student_Phone_Number;

    @Positive
    private int student_Roll_No;

    @NotNull(message = "Enter valid age")
    @Positive
    private Integer student_Age;
 
    private String student_Gender;

    @NotBlank(message = "Enter Parents Name...")
    private String student_Parents_Name;

    @Size(min = 10,max = 10,message = "Enter 10 digit phone number")
    private String student_Parents_Mobile_Number;

    @NotNull(message = "Mention Student Standard")
    private Integer student_Standard;

    @Positive
    private long student_Height;

    @Positive
    private long student_Weight;


    private String studentParentsEmail;

}
