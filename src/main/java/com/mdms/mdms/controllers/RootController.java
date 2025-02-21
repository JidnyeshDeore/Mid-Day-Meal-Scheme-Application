package com.mdms.mdms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mdms.mdms.entites.School;
import com.mdms.mdms.helpers.HelperForUserName;
import com.mdms.mdms.service.SchoolService;


@ControllerAdvice
public class RootController {

    @Autowired
    private SchoolService schoolService;

    @ModelAttribute
    public void addLoggedInUser(Model model,Authentication authentication){

        if(authentication==null){
            return ;
        }

        String email=HelperForUserName.getEmailOfLoggedInUser(authentication);
        // System.out.println("RootController---->"+email);

        School school=schoolService.getSchoolByEmail(email);

        if(school==null){
            System.out.println("Null School ");
            model.addAttribute("loggedInSchool", null);
        }else{
            System.out.println(" School with email is Logged in: "+school.getSchool_Email());
            model.addAttribute("loggedInSchool", school);
        }

    }
}
