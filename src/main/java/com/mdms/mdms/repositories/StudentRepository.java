package com.mdms.mdms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mdms.mdms.entites.School;
import com.mdms.mdms.entites.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>{

    Optional<Student> findByStudentEmail(String studentEmail);


    Page<Student> findBySchool(School school, Pageable pageable);
    

    Page<Student> findBySchoolAndStudentNameContaining(School school, String nameKeyword, Pageable pageable);

    Page<Student> findBySchoolAndStudentEmailContaining(School school, String emailkeyword, Pageable pageable);

    Page<Student> findBySchoolAndStudentPhoneNumberContaining(School school, String phonekeyword, Pageable pageable);
    
    @Query("SELECT s FROM Student s WHERE s.school.school_Name = :schoolName")
    List<Student> findStudentsBySchoolName(@Param("schoolName") String schoolName);


    @Query(value = "SELECT * FROM student_table WHERE school_id = :schoolId", nativeQuery = true)
    List<Student> findBySchool_SchoolId(@Param("schoolId") String schoolId);


}
