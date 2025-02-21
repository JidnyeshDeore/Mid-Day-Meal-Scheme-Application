package com.mdms.mdms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mdms.mdms.dto.AssignRequestDTO;
import com.mdms.mdms.entites.MealRequest;
import com.mdms.mdms.entites.School;
import com.mdms.mdms.entites.Supplier;
import com.mdms.mdms.service.MealRequestService;
import com.mdms.mdms.service.SchoolService;
import com.mdms.mdms.service.SupplierService;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private SchoolService schoolService;

    @Autowired
    private MealRequestService mealRequestService;

    @Autowired
    private SupplierService supplierService;


    @GetMapping("/dashboard")
    public String dashboard(){

        return "admin/admin_dashboard";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam("password") String password, 
                        RedirectAttributes redirectAttributes) {


        System.out.println(email);
        System.out.println(password);
        // Validate credentials
        if ("admin@gmail.com".equals(email) && "123456".equals(password)) {
            // Successful login, redirect to the dashboard
            return "admin/admin_dashboard"; // or to wherever you want to redirect
        } else {
            // Invalid login, return an error message or redirect to the login page
            redirectAttributes.addFlashAttribute("error", "Invalid credentials!");
            return "redirect:/admin/login";  // Redirect back to login page
        }
    }



    @GetMapping("/view-schools")
    public String getAllSchools(org.springframework.ui.Model model) {
        List<School> schools = schoolService.getAllSchools();
        System.out.println("Inside view School");
        model.addAttribute("schools", schools);
        return "admin/view_schools";
    }

    @GetMapping("/school/{school_Id}")
    @ResponseBody
    public School getSchool(@PathVariable String school_Id){

        School school=schoolService.getSchoolById(school_Id);
        return school;
    }

    @GetMapping("/view-allMealRequest")
    public String viewAllMealRequests(Model model){

        List<MealRequest>mealRequests= mealRequestService.viewMealRequests();
        model.addAttribute("mealRequests",mealRequests);
        return "admin/view_mealRequests";
    }

    @GetMapping("/view-meal/{mealRequestId}")
    @ResponseBody
    public MealRequest getSpecificMealRequest(@PathVariable String mealRequestId){

        MealRequest request=mealRequestService.getSpecificMealRequest(mealRequestId);
        return request;
    }


    @PostMapping("/assign-supplier/{mealRequestId}")
    public ResponseEntity<?> assignSupplier(
        @PathVariable String mealRequestId,
        @RequestParam String supplierId) {

        MealRequest mealRequest = mealRequestService.getSpecificMealRequest(mealRequestId);
        com.mdms.mdms.entites.Supplier supplier = supplierService.getSupplierById(supplierId);

        if (mealRequest == null || supplier == null) {
            return ResponseEntity.badRequest().body("Invalid Meal Request or Supplier ID");
        }

        mealRequest.setAssignedSupplier(supplier);
        mealRequest.setStatus(MealRequest.Status.ASSIGNED);
        mealRequestService.save(mealRequest);

        return ResponseEntity.ok("Supplier assigned successfully");
    }


    // Endpoint to Fetch Supplier-Meal Relationships:
    @GetMapping("/supplier-meal-requests/{supplierId}")
    public ResponseEntity<?> getMealRequestsForSupplier(@PathVariable String supplierId) {
        Supplier supplier = supplierService.getSupplierById(supplierId);

        if (supplier == null) {
            return ResponseEntity.badRequest().body("Invalid Supplier ID");
        }
        System.out.println("----------------------------------------------------");
        return ResponseEntity.ok(supplier.getMealRequests());
    }

    @PatchMapping("/update-status/{mealRequestId}")
    public ResponseEntity<?> updateMealRequestStatus(
        @PathVariable String mealRequestId,
        @RequestParam MealRequest.Status status) {

        MealRequest mealRequest = mealRequestService.getSpecificMealRequest(mealRequestId);
        if (mealRequest == null) {
            return ResponseEntity.badRequest().body("Invalid Meal Request ID");
        }

        mealRequest.setStatus(status);
        mealRequestService.save(mealRequest);

        return ResponseEntity.ok("Meal Request status updated successfully");
    }


    @GetMapping("/suppliers")
    @ResponseBody
    public List<Supplier>getAllSuppliers(){
        System.out.println("-------------------");
        List<Supplier>allSuppliers=supplierService.getAllSupplier();
        return allSuppliers;
    }



    @GetMapping("/view-suppliers")
    public String getAllSuppliers(org.springframework.ui.Model model) {
        List<Supplier> suppliers = supplierService.getAllSupplier();
        System.out.println("Inside view Supplier");
        model.addAttribute("suppliers", suppliers);
        return "admin/view_suppliers";
    }


    @GetMapping("/supplier/{supplierId}")
    @ResponseBody
    public Supplier getSpecSupplier(@PathVariable String supplierId){

        Supplier supplier= supplierService.getSupplierById(supplierId);
        return supplier;
    }



    @GetMapping("/assign-meal-requests")
    public String viewAssignmentPage(Model model) {
        List<MealRequest> pendingRequests = mealRequestService.getPendingOrApprovedRequests();
        List<Supplier> activeSuppliers = supplierService.getActiveSuppliers();

        model.addAttribute("mealRequests", pendingRequests);
        model.addAttribute("suppliers", activeSuppliers);

        return "/admin/assignMealRequests"; // Thymeleaf page name
    }


    @PostMapping("/api/assignMealRequest")
    public ResponseEntity<String> assignMealRequest(@RequestBody AssignRequestDTO assignRequestDTO) {
        MealRequest mealRequest = mealRequestService.getSpecificMealRequest(assignRequestDTO.getMealRequestId());
        Supplier supplier = supplierService.getSupplierById(assignRequestDTO.getSupplierId());

        if (mealRequest == null || supplier == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid meal request or supplier ID.");
        }

        if (mealRequest.getStatus() != MealRequest.Status.PENDING && mealRequest.getStatus() != MealRequest.Status.APPROVED) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Meal request is not eligible for assignment.");
        }

        mealRequest.setAssignedSupplier(supplier);
        mealRequest.setStatus(MealRequest.Status.ASSIGNED);
        mealRequestService.save(mealRequest);

        return ResponseEntity.ok("Meal request assigned successfully.");
    }
    
}
