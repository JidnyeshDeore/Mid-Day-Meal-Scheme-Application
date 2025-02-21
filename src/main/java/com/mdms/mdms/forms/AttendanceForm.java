package com.mdms.mdms.forms;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AttendanceForm {

    private LocalDate date;
    private boolean attended; // Whether the student was present
    private boolean receivedMeal=true;
}
