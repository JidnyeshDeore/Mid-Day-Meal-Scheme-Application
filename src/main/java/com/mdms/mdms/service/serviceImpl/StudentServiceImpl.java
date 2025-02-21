package com.mdms.mdms.service.serviceImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdms.mdms.entites.Attendance;
import com.mdms.mdms.entites.School;
import com.mdms.mdms.entites.Student;
import com.mdms.mdms.repositories.AttendanceRepository;
import com.mdms.mdms.repositories.StudentRepository;
import com.mdms.mdms.service.StudentService;


@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Student saveStudent(Student student) {

        final  String mdmName="STUDENT_MDM_";
        String studentId=UUID.randomUUID().toString();
        student.setStudentId(studentId);

        student.setStudent_Password(passwordEncoder.encode(student.getStudent_Password()));


        // String studentName=student.getStudentName();
        // // String schoolId=student.getSchool().getSchool_Id();
        // String schoolName=student.getSchool().getSchool_Name();
        // String studentRollNo=Integer.toString(student.getStudent_Roll_No());

        String studentMDMID=mdmName + UUID.randomUUID().toString().substring(0, 5);

        student.setStudent_mdm_id(studentMDMID);
        
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentByEmail(String userName) {

        return studentRepository.findByStudentEmail(userName).orElse(null);

    }

    @Override
    public Page<Student> getByUserName(School school, int page, int size, String sortBy, String direction) {
        
        Sort sort=direction.equals("desc") 
            ? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        // System.out.println(sort);
        var pageable=PageRequest.of(page, size,sort);
        // System.out.println(pageable);
        // Student student=(Student) studentRepository.findBySchool(school,pageable);
        Page<Student> studentsPage = studentRepository.findBySchool(school, pageable);
    
        // Log the details of the returned Page
        // System.out.println("Total Elements: " + studentsPage.getTotalElements());
        // System.out.println("Total Pages: " + studentsPage.getTotalPages());
        // System.out.println("Content: " + studentsPage.getContent());
        
        return studentsPage;


    }

    @Override
    public Page<Student> searchByName(String nameKeyword, int size, int page, String sortBy, String direction, School school) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return studentRepository.findBySchoolAndStudentNameContaining(school, nameKeyword, pageable);
    }

    @Override
    public Page<Student> searchByEmail(String emailKeyWord, int size, int page, String sortBy, String direction, School school) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return studentRepository.findBySchoolAndStudentEmailContaining(school, emailKeyWord, pageable);
    }

    @Override
    public Page<Student> searchByPhoneNumber(String phoneKeyWord, int size, int page, String sortBy, String direction, School school) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return studentRepository.findBySchoolAndStudentPhoneNumberContaining(school, phoneKeyWord, pageable);
    }

    @Override
    public Student getByStudentId(String studentId) {

        return studentRepository.findById(studentId).orElse(null);
      
    }

    @Override
    public void deleteStudent(String id) {

        studentRepository.deleteById(id);
    }

    @Override
    public Student updateStudent(Student student) {

        var studentOld=studentRepository.findById(student.getStudentId()).orElseThrow(()->new UsernameNotFoundException("Student Not Found: "));

        studentOld.setStudentName(student.getStudentName());
        studentOld.setStudentEmail(student.getStudentEmail());
        studentOld.setStudent_Address(student.getStudent_Address());
        studentOld.setStudentPhoneNumber(student.getStudentPhoneNumber());
        studentOld.setStudent_Parents_Mobile_Number(student.getStudent_Parents_Mobile_Number());
        studentOld.setStudent_Age(student.getStudent_Age());
        studentOld.setStudent_Standard(student.getStudent_Standard());
        studentOld.setStudent_Height(student.getStudent_Height());
        studentOld.setStudent_Weight(student.getStudent_Weight());
        studentOld.setStudent_Roll_No(student.getStudent_Roll_No());

        return studentRepository.save(studentOld);
    }

    @Override
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    @Override
    public List<Student> getStudentsBySchoolName(String schoolName) {
        
        return studentRepository.findStudentsBySchoolName(schoolName);
    }





    // Method to fetch attendance details for a student
    public List<Attendance> getAttendanceByStudent(String studentId) {
        return attendanceRepository.findByStudent_StudentId(studentId);
    }

    // BMI Calculation Logic
    public String calculateBMIWithCategory(Student student) {
        double heightInMeters = student.getStudent_Height() / 100.0; // Convert to meters
        double bmi = student.getStudent_Weight() / (heightInMeters * heightInMeters);
        
        // Determine the category
        String category;
        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi < 25) {
            category = "Normal weight";
        } else if (bmi < 30) {
            category = "Overweight";
        } else {
            category = "Obesity";
        }
        
        // Return BMI with category
        return String.format("%.2f (%s)", bmi, category);
    }
    // Method to send an email to parents
    public void sendEmailToParents(Student student, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println("going to sending mails.........");
        // message.setFrom("deorejidnyesh24@gmail.com");
        message.setTo(student.getStudentParentsEmail());
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    // Send daily update emails for a specific school
    @Override
    public void sendDailyUpdateEmails(String schoolId) {
        List<Student> students = studentRepository.findBySchool_SchoolId(schoolId);
        for (Student student : students) {
            String subject = "Daily Update for " + student.getStudentName();
            String body = "Dear Parent,\n\nHere is the daily update for your child, " + student.getStudentName() +
                    ".\n\nPlease review the information and contact the school if you have any concerns.\n\n";
            
            // Add Attendance and Meal Information
            List<Attendance> attendanceList = getAttendanceByStudent(student.getStudentId());
            String attendanceDetails = attendanceList.stream()
                    .map(attendance -> "Date: " + attendance.getDate() + ", Attended: " + attendance.isAttended() +
                            ", Meal: " + (attendance.isReceivedMeal() ? "Received" : "Not Received"))
                    .collect(Collectors.joining("\n"));
            
            body += "\nAttendance Details:\n" + attendanceDetails;
            
            // Add BMI Information
               String bmiWithCategory = calculateBMIWithCategory(student);
               body += "\n\nMalnutrition Status (BMI): " + bmiWithCategory;

               body += "\n\nVerify it with MDM_ID of your child " + student.getStudent_mdm_id();
            sendEmailToParents(student, subject, body);
        }
    }

    @Override
    public List<Student> getStudentsBySchool(String schoolId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStudentsBySchool'");
    }



    
}
