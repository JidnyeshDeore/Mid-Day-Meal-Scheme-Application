package com.mdms.mdms.entites;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "attendance_table")
@Data
public class Attendance {

    @Id
    private String attendanceId;// Unique identifier for the attendance record

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "studentId", nullable = false)
    @JsonIgnore // Mark this side to be serialized
    private Student student;


    private LocalDate date;
    private boolean attended; // Whether the student was present
    private boolean receivedMeal; // Whether the student received a meal

    // Additional fields as needed


    // Custom toString method
    @Override
    public String toString() {
        return "Attendance{" +
                "attendaceId='" + attendanceId + '\'' +
                ", studentId='" + (student != null ? student.getStudentId() : "N/A") + '\'' +  // Print studentId if student is not null +  // Print mealRequestId if mealRequest is not null
                ", date=" + date +
                ", attended=" + attended +
                ", receivedMeal=" + receivedMeal +
                '}';
    }
}
