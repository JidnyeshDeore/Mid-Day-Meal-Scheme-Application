package com.mdms.mdms.entites;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "student_table")
@Data
public class Student {

    @Id
    public String studentId;
    private String studentName;
    private String studentEmail;
    private String student_Password;
    private String student_Address;
    private String studentPhoneNumber;
    private int student_Roll_No;
    private Integer student_Age;
    private String student_mdm_id;
    private String student_Gender;
    private String student_Parents_Name;
    private String student_Parents_Mobile_Number;
    

    private String studentParentsEmail;

    @ManyToOne
    @JoinColumn(name = "school_Id",referencedColumnName = "school_Id",nullable=false)
    private School school;
    private Integer student_Standard;
    private long student_Height;
    private long student_Weight;


    @OneToMany(mappedBy = "student",cascade=CascadeType.ALL)
    private List<Attendance>attendances;
}
