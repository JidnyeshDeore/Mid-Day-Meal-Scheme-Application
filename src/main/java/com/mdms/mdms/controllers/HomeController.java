package com.mdms.mdms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mdms.mdms.config.LoggedInSupplier;
import com.mdms.mdms.entites.School;
import com.mdms.mdms.entites.Supplier;
import com.mdms.mdms.forms.SchoolForm;
import com.mdms.mdms.forms.SupplierForm;
import com.mdms.mdms.service.SchoolService;
import com.mdms.mdms.service.SupplierService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private LoggedInSupplier loggedInSupplier;


    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/base")
    public String base(){
        return "base";
    }

    @GetMapping("/school-registration")
    public String school_Registration_Form(Model model, HttpSession session){
        System.out.println("HomeController:-->Inside School Registration form method: GET");

        SchoolForm schoolForm=new SchoolForm();

        //blank schoolForm school_Registration.html me bhejna hai...
        model.addAttribute("schoolForm", schoolForm);
        // model.addAttribute("message", session.getAttribute("message"));
        return "school_Registration";
    }

    @PostMapping("/school-registration-processing")
    public String school_Registration_Processing(
            @Valid @ModelAttribute SchoolForm schoolForm, 
            BindingResult result, 
            RedirectAttributes redirectAttributes,
            Model model) {
    
        System.out.println("HomeController:-->Inside School Registration form method: POST");
    
        // Check for validation errors
        if (result.hasErrors()) {
            System.out.println("Validation errors occurred");
    
            // Return to the form with the error details
            model.addAttribute("schoolForm", schoolForm);
            model.addAttribute("message", "Error: Please correct the highlighted fields.");
            return "school_Registration";
        }
    
        try {
            // Attempt to register the school
            School school = new School();
            school.setSchool_Name(schoolForm.getSchool_Name());
            school.setSchool_Address(schoolForm.getSchool_Address());
            school.setSchool_Email(schoolForm.getSchool_Email());
            school.setSchool_Password(schoolForm.getSchool_Password());
            school.setSchool_Phone_Number(schoolForm.getSchool_Phone_Number());
            school.setSchool_mdm_Instructor(schoolForm.getSchool_mdm_Instructor());
            school.setSchool_Strength(schoolForm.getSchool_Strength());
    
            System.out.println(school.toString());
            schoolService.saveSchool(school);
    
            // On success, add a success message
            redirectAttributes.addFlashAttribute("message", "School registered successfully!");
            return "redirect:/school-registration";
    
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error registering school: " + e.getMessage());
    
            // Add an error message and return to the form
            model.addAttribute("schoolForm", schoolForm);
            model.addAttribute("message", "Error: Could not register the school. Please try again later.");
            return "school_Registration";
        }
    }
    


    @GetMapping("/school-login")
    public String school_Login_Form(){
        System.out.println("HomeController:-->Inside School Login form method");
        return "school_login";
    }

    @GetMapping("/supplier-registration")
    public String supplier_Registration_Form(Model model,HttpSession session){

        System.out.println("HomeController:-->Inside Supplier Registration form method");

        SupplierForm supplierForm=new SupplierForm();
        model.addAttribute("supplierForm", supplierForm);
        // model.addAttribute("message", session.getAttribute("message"));
        return "supplier_Registration";
    }

    @PostMapping("/supplier-registration-processing")
    public String supplier_Registration_Processing(@Valid @ModelAttribute SupplierForm supplierForm
        ,BindingResult result
        ,HttpSession session
        ,RedirectAttributes redirectAttributes){

        System.out.println("HomeController:-->Inside Supplier Registration form method: POST");
        if(result.hasErrors()){
            System.out.println("Error has occured in Supplier Registration");
            return "supplier_Registration";
        }

        Supplier supplier=new Supplier();
        supplier.setSupplierName(supplierForm.getSupplierName());
        supplier.setSupplierAddress(supplierForm.getSupplierAddress());
        supplier.setSupplierEmail(supplierForm.getSupplierEmail());
        supplier.setSupplierContactNumber(supplierForm.getSupplierContactNumber());
        supplier.setSupplierPassword(supplierForm.getSupplierPassword());
        supplier.setFSSAIRegistrationNumber(supplierForm.getFSSAIRegistrationNumber());

        Supplier s1=supplierService.saveSupplier(supplier);
        System.out.println(s1.toString());
        redirectAttributes.addFlashAttribute("message", "Supplier registered successfully!");

        
        return "redirect:/supplier-registration";
    }


    @GetMapping("/supplier-login")
    public String supplier_Login_Form(){
        System.out.println("HomeController:-->Inside Supplier login form method");
        return "supplier_login";
    }

    @GetMapping("/supplier-logout")
    public String supplier_Logout_Form(){
        System.out.println("HomeController:-->Inside Supplier logout form method");
        return "supplier_login";
    }

    @GetMapping("/parent-registration")
    public String parent_Registration_Form(){
        System.out.println("HomeController:-->Inside Parent Registration form method");
        return "parent_Registration";
    }

    @GetMapping("/parent-login")
    public String parent_Login_Form(){
        System.out.println("HomeController:-->Inside Parent login form method");
        return "parent_login";
    }

    @PostMapping("/supplier-authenticate")
    public String authenticate(HttpServletRequest request,RedirectAttributes redirectAttributes) {
        
        String email=request.getParameter("supplierEmail");
        System.out.println("Authentication attempt with: " +email );

        Supplier supplier=supplierService.findBySupplierEmail(email);
        loggedInSupplier.setSupplier(supplier);
        if(supplier==null){
            redirectAttributes.addFlashAttribute("message", "Invalid Supplier Login" );
            return "redirect:/supplier-login";
        }
        // System.out.println("Logged in Supplier: " + supplier);
        return "redirect:/supplier/dashboard";
    }


    @GetMapping("/admin/login")
    public String adminLoginPage(){

        return "admin_login";   
    }




}
