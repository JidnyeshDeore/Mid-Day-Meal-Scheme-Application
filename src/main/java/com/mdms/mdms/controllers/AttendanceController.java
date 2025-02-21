package com.mdms.mdms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdms.mdms.dto.AttendanceUpdateRequest;
import com.mdms.mdms.entites.School;
import com.mdms.mdms.entites.Student;
import com.mdms.mdms.service.AttendanceService;
import com.mdms.mdms.service.StudentService;

@Controller
@RequestMapping("/school/attendance")
public class AttendanceController {


    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;


    @GetMapping("/view-attendance")
    public String attendancePage(@AuthenticationPrincipal School school,Model model){

        String schoolName=school.getSchool_Name();
        List<Student>studentsList=studentService.getStudentsBySchoolName(schoolName);
        model.addAttribute("studentsList", studentsList);
        return "school/update_attendance";
    }



    @PostMapping("/update")
    public ResponseEntity<String> updateAttendance(@RequestBody AttendanceUpdateRequest request) {
        try {
            attendanceService.updateAttendance(request);
            return ResponseEntity.ok("Attendance updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    

    @GetMapping("/{studentId}")
    @ResponseBody
    public Student getStudent(@PathVariable String studentId){

        Student student=studentService.getByStudentId(studentId);
        return student;
    }

    

}
