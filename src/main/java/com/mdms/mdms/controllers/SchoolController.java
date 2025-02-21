package com.mdms.mdms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdms.mdms.entites.School;
import com.mdms.mdms.entites.Student;
import com.mdms.mdms.entites.Supplier;
import com.mdms.mdms.forms.StudentForm;
import com.mdms.mdms.forms.StudentSearchForm;
import com.mdms.mdms.helpers.AppConstants;
import com.mdms.mdms.helpers.HelperForUserName;
import com.mdms.mdms.service.SchoolService;
import com.mdms.mdms.service.StudentService;
import com.mdms.mdms.service.SupplierService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SupplierService supplierService;
 
    @RequestMapping("/dashboard")
    public String schoolDashboard(){

        return "school/dashboard";
    }

    @GetMapping("/add-student")
    public String add_Student_Form(Model model,HttpSession session){

        System.out.println("SchoolController--->Inside add_Student_Form: GET");
        StudentForm studentForm=new StudentForm();
        model.addAttribute("studentForm",studentForm);
        model.addAttribute("message", session.getAttribute("message"));
        return "school/add_Student";
    }

    @PostMapping("/student-registration-processing")
    public String student_Registration_Processing(@Valid @ModelAttribute StudentForm studentForm
    ,BindingResult result
    ,@AuthenticationPrincipal School school
    ,HttpSession session){

        System.out.println("SchoolController--->Inside student_Registration_Processing: POST");
        if(result.hasErrors()){
            System.out.println("Getting errors while adding Student........");
            return "school/add_Student";
        }

        Student student=new Student();
        student.setStudentName(studentForm.getStudent_Name());
        student.setStudentEmail(studentForm.getStudent_Email());
        student.setStudent_Password(studentForm.getStudent_Password());
        student.setStudent_Address(studentForm.getStudent_Address());
        student.setStudentPhoneNumber(studentForm.getStudent_Phone_Number());
        student.setStudent_Roll_No(studentForm.getStudent_Roll_No());
        student.setStudent_Age(studentForm.getStudent_Age());
        student.setStudent_Gender(studentForm.getStudent_Gender());
        student.setStudent_Parents_Name(studentForm.getStudent_Parents_Name());
        student.setStudent_Parents_Mobile_Number(studentForm.getStudent_Parents_Mobile_Number());
        student.setStudent_Standard(studentForm.getStudent_Standard());
        student.setStudent_Height(studentForm.getStudent_Height());
        student.setStudent_Weight(studentForm.getStudent_Weight());


        student.setStudentParentsEmail(studentForm.getStudentParentsEmail());



        student.setSchool(school);
        System.out.println("Student Add:-->: "+student.toString());

        studentService.saveStudent(student);

        session.setAttribute("message", "Student Register Successfully..!!");
        return "redirect:/school/add-student";
    }

    @GetMapping("/view-students")
    public String viewStudents(
        @RequestParam( value="page",defaultValue="0")int page,
        @RequestParam(value="size",defaultValue="8") int size,
        @RequestParam(value="sortBy",defaultValue="studentName") String sortBy,
        @RequestParam(value="direction",defaultValue="asc") String direction,
        Model model,
        Authentication authentication,
        HttpSession session){

            String userName=HelperForUserName.getEmailOfLoggedInUser(authentication);
            System.out.println("/school/view-students---->USERNAME: "+userName);

            // Retrieve the school details associated with that email
            School school=schoolService.getSchoolByEmail(userName);

            // System.out.println(school.toString());
            
            Page<Student>pageStudent= studentService.getByUserName(school,page,size,sortBy,direction); //getByUser
            
            // System.out.println(pageStudent.toString());
            
            model.addAttribute("pageStudent",pageStudent);

            model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

            model.addAttribute("studentSearchForm", new StudentSearchForm());

            String message = (String) session.getAttribute("message");
            if (message != null) {
                model.addAttribute("message", message);
                session.removeAttribute("message"); // Clear the message after displaying it
            }
            

        return "school/view_students";

    }



    @RequestMapping("/student/search")
    public String searchHandler(
        @ModelAttribute StudentSearchForm studentSearchForm,
        @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "sortBy", defaultValue = "studentName") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Model model,
        Authentication authentication
    ){


        var school = schoolService.getSchoolByEmail(HelperForUserName.getEmailOfLoggedInUser(authentication));

        Page<Student> pageStudent = null;
        if (studentSearchForm.getField().equalsIgnoreCase("studentName")) {
            pageStudent = studentService.searchByName(studentSearchForm.getValue(), size, page, sortBy, direction,school);
            System.out.println(pageStudent.toString());
                    
        } else if (studentSearchForm.getField().equalsIgnoreCase("studentEmail")) {
            pageStudent = studentService.searchByEmail(studentSearchForm.getValue(), size, page, sortBy, direction,school);
            
        } else if (studentSearchForm.getField().equalsIgnoreCase("studentPhoneNumber")) {
            pageStudent = studentService.searchByPhoneNumber(studentSearchForm.getValue(), size, page, sortBy,direction, school);
                    
        }
        // System.out.println(pageStudent.toString());
       
        model.addAttribute("studentSearchForm", studentSearchForm);

        model.addAttribute("pageStudent", pageStudent);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "school/search";
    }

    @GetMapping("/student/{studentId}")
    @ResponseBody
    public Student getSpecificStudentById(@PathVariable String studentId){
        System.out.println("Deatials of specific Student");
        Student student= studentService.getByStudentId(studentId);
        return student;
    }


    @RequestMapping("/student/delete-student/{id}")
    public String deleteStudent(@PathVariable("id") String id, HttpSession session){


        studentService.deleteStudent(id);
        System.out.println("Student deleted with id: "+id);


        session.setAttribute("message", "Student Deleted Successfully..!!");
        return "redirect:/school/view-students";

    }

    @GetMapping("/student/update-student-view/{studentId}")
    public String updateStudentView(@PathVariable("studentId") String studentId
    ,Model model
    ,HttpSession session){

      
        var student=studentService.getByStudentId(studentId);
  
        StudentForm studentForm=new StudentForm();

        studentForm.setStudent_Name(student.getStudentName());
        studentForm.setStudent_Email(student.getStudentEmail());
        studentForm.setStudent_Phone_Number(student.getStudentPhoneNumber());
        studentForm.setStudent_Address(student.getStudent_Address());
        
        studentForm.setStudent_Parents_Mobile_Number(student.getStudent_Parents_Mobile_Number());
        studentForm.setStudent_Age(student.getStudent_Age());
        studentForm.setStudent_Standard(student.getStudent_Standard());
        studentForm.setStudent_Height(student.getStudent_Height());
        studentForm.setStudent_Weight(student.getStudent_Weight());
        studentForm.setStudent_Roll_No(student.getStudent_Roll_No());

        //not displaying 
        studentForm.setStudent_Password(student.getStudent_Password());
        studentForm.setStudent_Parents_Name(student.getStudent_Parents_Name());

        student.setStudentParentsEmail(studentForm.getStudentParentsEmail());

        
        model.addAttribute("studentForm", studentForm);
        model.addAttribute("studentId", studentId);

        model.addAttribute("message", session.getAttribute("message"));
        return "school/update_student";
    }

    @PostMapping("/student/update/{studentId}")
    public String updateStudent(@PathVariable("studentId")String studentId,
    @Valid 
    @ModelAttribute StudentForm studentForm
    ,BindingResult result
    ,Model model
    ,@AuthenticationPrincipal School school
    ,HttpSession session){

        System.out.println("Student ID------>"+studentId);
        System.out.println(studentForm.toString());
        if(result.hasErrors()){
            System.out.println("Error occured....................");
            result.getAllErrors().forEach(error -> {
                System.out.println("Validation error: " + error.getDefaultMessage());
            });
    
            // Optionally, you can add these errors to the model to display them on the page
            model.addAttribute("errors", result.getAllErrors());
            return "school/update_student";
        }


        var student=studentService.getByStudentId(studentId);

        student.setStudentId(studentId);
        student.setStudentName(studentForm.getStudent_Name());
        student.setStudentEmail(studentForm.getStudent_Email());
        student.setStudentPhoneNumber(studentForm.getStudent_Phone_Number());
        student.setStudent_Address(studentForm.getStudent_Address());
        student.setStudent_Parents_Mobile_Number(studentForm.getStudent_Parents_Mobile_Number());
        student.setStudent_Age(studentForm.getStudent_Age());
        student.setStudent_Standard(studentForm.getStudent_Standard());
        student.setStudent_Height(studentForm.getStudent_Height());
        student.setStudent_Weight(studentForm.getStudent_Weight());
        student.setStudent_Roll_No(studentForm.getStudent_Roll_No());

        student.setStudentParentsEmail(studentForm.getStudentParentsEmail());

        student.setSchool(school);
        var updateStudent=studentService.updateStudent(student);
        System.out.println("Updated Student: "+updateStudent);
        session.setAttribute("message", "Contact Updated Successfully..!!");
        return "redirect:/school/student/update-student-view/" +studentId;
    }

    @GetMapping("/view-suppliers")
    @ResponseBody
    public List<Supplier>getAllSupplier(){

        System.out.println("View Suppliers");
        return supplierService.getAllSupplier();
    }


            // Get all students of a specific school by schoolId
            @GetMapping("/studentReport")
            public String getStudentsBySchool(@AuthenticationPrincipal School school, Model model) {
                
                String schoolName=school.getSchool_Name();
                List<Student>studentList=studentService.getStudentsBySchoolName(schoolName);
                model.addAttribute("studentList", studentList);
                return "school/studentReport";
            }
        
            // Trigger the sending of daily update emails for a school
            @PostMapping("/sendDailyUpdates")
            @ResponseBody
            public String sendDailyUpdates(@AuthenticationPrincipal School school,HttpSession session) {


                studentService.sendDailyUpdateEmails(school.getSchool_Id());

                System.out.println("going to sending mails.......SChOOLCONTROLLER..");
                
                return "redirect:/school/sendDailyUpdates";
            }
}
