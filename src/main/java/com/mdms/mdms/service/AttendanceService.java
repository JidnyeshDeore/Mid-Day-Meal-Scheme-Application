package com.mdms.mdms.service;

import java.time.LocalDate;

import com.mdms.mdms.dto.AttendanceUpdateRequest;
import com.mdms.mdms.entites.Attendance;

public interface AttendanceService {


    public Attendance markAttendance(String studentId, Boolean receivedMeal, Boolean attended, LocalDate date);

    public void updateAttendance(AttendanceUpdateRequest request);


}
