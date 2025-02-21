package com.mdms.mdms.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AttendanceUpdateRequest {


    private String studentId;
    private LocalDate date;
    private boolean attended;
    private boolean receivedMeal;

}
