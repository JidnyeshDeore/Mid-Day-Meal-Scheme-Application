package com.mdms.mdms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mdms.mdms.entites.School;
import com.mdms.mdms.entites.Student;


public interface StudentService {

    public Student saveStudent(Student student);

    public Student getStudentByEmail(String userName);

    public Page<Student> getByUserName(School school, int page, int size, String sortBy, String direction);

    public Page<Student> searchByName(String value, int size, int page, String sortBy, String direction, School school);

    public Page<Student> searchByEmail(String value, int size, int page, String sortBy, String direction, School school);

    public Page<Student> searchByPhoneNumber(String value, int size, int page, String sortBy, String direction,
            School school);

    Student getByStudentId(String studentId);

    public void deleteStudent(String id);

    public Student updateStudent(Student student);

    public List<Student>getAllStudents();

    public List<Student>getStudentsBySchoolName(String schoolName);

    public List<Student> getStudentsBySchool(String schoolId);

    public void sendDailyUpdateEmails(String schoolId);




}
