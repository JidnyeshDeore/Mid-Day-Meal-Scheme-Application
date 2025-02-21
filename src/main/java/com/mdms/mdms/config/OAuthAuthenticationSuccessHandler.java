package com.mdms.mdms.config;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mdms.mdms.entites.School;
import com.mdms.mdms.repositories.SchoolRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        var oauth2AuthenticationToken= (OAuth2AuthenticationToken)authentication;

        String authorizedClientRegistrartionId=oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        System.out.println("Authetized Client Registration ID:  "+authorizedClientRegistrartionId);

        var oauth_School=(DefaultOAuth2User)authentication.getPrincipal();
        System.out.println(oauth_School.toString());
        School school=new School();
        school.setSchool_Id(UUID.randomUUID().toString());
        school.setSchool_EmailVerified(true);
        school.setEnabled(true);
        school.setSchool_Password("school");

        if(authorizedClientRegistrartionId.equalsIgnoreCase("google")){

            school.setSchool_Email(oauth_School.getAttribute("email"));
            school.setSchool_Name(oauth_School.getAttribute("name"));
            String schoolEmail = school.getSchool_Email();
            // Ensure schoolId has at least 7 characters
            String schoolId=school.getSchool_Id();
            String schoolIdSubstring = schoolId.length() >= 7 ? schoolId.substring(0, 7) : schoolId;
            // Concatenate the email with the first 7 characters of the school ID
            String school_mdm_id = schoolEmail + schoolIdSubstring;
            school.setMdm_Id(school_mdm_id);
        }

        School existingSchool=schoolRepository.findBySchoolEmail(school.getSchool_Email()).orElse(null);

        if(existingSchool==null){
            System.out.println("School Using Goggle Login is Saved: ..................");
            schoolRepository.save(school);
        }


        new DefaultRedirectStrategy().sendRedirect(request, response, "/school/dashboard");
    }   




}
