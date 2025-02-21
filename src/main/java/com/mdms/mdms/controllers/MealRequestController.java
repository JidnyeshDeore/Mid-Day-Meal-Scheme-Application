package com.mdms.mdms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mdms.mdms.entites.MealRequest;
import com.mdms.mdms.entites.School;
import com.mdms.mdms.forms.MealRequestForm;
import com.mdms.mdms.service.MealRequestService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/school/mealRequests")
public class MealRequestController {


    @Autowired
    private MealRequestService mealRequestService;

    @GetMapping("/add")
    public String addMealRequestForm(Model model,HttpSession session){

        System.out.println("Inside add meal request form");
        model.addAttribute("mealRequestForm",new MealRequestForm());
        model.addAttribute("message", session.getAttribute("message"));
        return "school/add_MealRequest";
    }

    @PostMapping("/add")
    public String createMealRequest(
        @Valid @ModelAttribute MealRequestForm mealRequestForm
        ,BindingResult result
        ,@AuthenticationPrincipal School school
        ,HttpSession session) {

        // System.out.println(mealRequestForm);

        if(result.hasErrors()){
            System.out.println("Getting errors while adding meal Request........");
            session.setAttribute("message", "Error at registering the meal.!!");

            result.getFieldErrors().forEach(error -> {
                System.out.println("Field: " + error.getField() + 
                                   ", Rejected Value: " + error.getRejectedValue() + 
                                   ", Message: " + error.getDefaultMessage());
            });

            return "school/add_MealRequest";
        }

        MealRequest newMealRequest=new MealRequest();
        newMealRequest.setMealItems(mealRequestForm.getMealItems());
        newMealRequest.setNutritionalRequirements(mealRequestForm.getNutritionalRequirements());
        newMealRequest.setDeliveryDate(mealRequestForm.getDeliveryDate());
        newMealRequest.setContactPerson(mealRequestForm.getContactPerson());
        newMealRequest.setContactPhone(mealRequestForm.getContactPhone());
        String id=school.getSchool_Id();
        MealRequest meal= mealRequestService.createMealRequest(id,newMealRequest);
        System.out.println(meal);
        session.setAttribute("message", "meal Register Successfully..!!");
        return "redirect:/school/mealRequests/add";
    }

    @GetMapping("/view-mealRequests")
    public String viewMealRequestOfSchool(@AuthenticationPrincipal School school, HttpSession session,Model model){

        String schoolId=school.getSchool_Id();
        // System.out.println(school);
        List<MealRequest> mealRequest=mealRequestService.getMealRequestBySchoolId(schoolId);
        // System.out.println(mealRequest);
        model.addAttribute("mealRequest", mealRequest);



        // Get and remove the message from the session
        Object message = session.getAttribute("message");
        if (message != null) {
            model.addAttribute("message", message);
            session.removeAttribute("message");
        }
        return "school/view_mealRequests";
    }

    @PostMapping("/update-status")
    public String updateMealRequestStatus(
        @AuthenticationPrincipal School school,
        @RequestParam("mealRequestId") String mealRequestId,
        @RequestParam("status") MealRequest.Status status,
        HttpSession session
    ) {
        try {
            mealRequestService.updateMealRequestStatus(school.getSchool_Id(), mealRequestId, status);
            session.setAttribute("message", "Meal request status updated successfully!");
        } catch (Exception e) {
            session.setAttribute("message", "Error updating meal request status: " + e.getMessage());
        }
        return "redirect:/school/mealRequests/view-mealRequests";
    }

}
