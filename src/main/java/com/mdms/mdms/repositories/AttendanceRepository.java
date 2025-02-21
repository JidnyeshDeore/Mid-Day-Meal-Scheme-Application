package com.mdms.mdms.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdms.mdms.entites.Attendance;
import com.mdms.mdms.entites.Student;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String>{

       // Custom query to find attendance records by student and date
    List<Attendance> findByStudent_StudentIdAndDate(String studentId,LocalDate date);
    

    Optional<Attendance> findByStudentAndDate(Student student, LocalDate date);

    List<Attendance> findByStudent_StudentId(String studentId);
}
