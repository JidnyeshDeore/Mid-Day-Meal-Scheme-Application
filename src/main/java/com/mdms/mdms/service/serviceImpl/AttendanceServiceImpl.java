package com.mdms.mdms.service.serviceImpl;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdms.mdms.dto.AttendanceUpdateRequest;
import com.mdms.mdms.entites.Attendance;
import com.mdms.mdms.entites.Student;
import com.mdms.mdms.repositories.AttendanceRepository;
import com.mdms.mdms.repositories.StudentRepository;
import com.mdms.mdms.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService{

    @Autowired
    private AttendanceRepository attendanceRepository;


    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Attendance markAttendance(String studentId, Boolean receivedMeal, Boolean attended, LocalDate date) {
       // Check if the student and meal request exist
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        
        // Create a new attendance record
        Attendance attendance = new Attendance();
        // String atdId=UUID.randomUUID().toString().substring(0, 10);
        // attendance.setAttendanceId(atdId);
        attendance.setStudent(student);
        attendance.setReceivedMeal(receivedMeal);
        attendance.setDate(date);
        attendance.setAttended(attended);

        return attendanceRepository.save(attendance); // Save the attendance record
    }

    @Override
    public void updateAttendance(AttendanceUpdateRequest request) {
        // Parse the date from the string format
        LocalDate attendanceDate = (request.getDate());
    
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
    
        // Check if attendance exists for the student and date
        Attendance attendance = attendanceRepository.findByStudentAndDate(student, attendanceDate)
                .orElseGet(() -> {
                    // Create a new attendance entry if it does not exist
                    Attendance newAttendance = new Attendance();
                    newAttendance.setStudent(student);
                    newAttendance.setDate(attendanceDate);
                    return newAttendance;
                });
    
        // Update attendance details
        attendance.setAttended(request.isAttended());
        attendance.setReceivedMeal(request.isReceivedMeal());
        String id=UUID.randomUUID().toString().substring(0,10);
        attendance.setAttendanceId(id);
        attendanceRepository.save(attendance); // Save the updated or new attendance
    }
    


    
}
