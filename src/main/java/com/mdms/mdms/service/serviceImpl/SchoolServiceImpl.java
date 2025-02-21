package com.mdms.mdms.service.serviceImpl;

import java.util.List;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdms.mdms.entites.School;
import com.mdms.mdms.repositories.SchoolRepository;
import com.mdms.mdms.service.SchoolService;

@Service
public class SchoolServiceImpl implements SchoolService{

    @Autowired
   private SchoolRepository schoolRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

    @Override
    public School saveSchool(School school) {

        final String mdmName="SCHOOL_MDM_";
        System.out.println("SchoolServiceImpl:-->Inside saveSchool method: ");
        String schoolId=UUID.randomUUID().toString();
        school.setSchool_Id(schoolId);

        //password ko encode karke set kar dena database me
        // System.out.println(school.getPassword());
        school.setSchool_Password(passwordEncoder.encode(school.getPassword()));
     
        String school_mdm_id = mdmName+UUID.randomUUID().toString().substring(0,5);
        school.setMdm_Id(school_mdm_id);


        //private boolean mdm_enrollment;
        //private boolean school_EmailVerified=false;

        
        return schoolRepository.save(school);

    }

    @Override
    public School getSchoolByEmail(String emailOfLoggedInUser) {
        return (School) schoolRepository.findBySchool_Email(emailOfLoggedInUser);
    }

    @Override
    public List<School> getAllSchools() {

        return schoolRepository.findAll();
    }

    @Override
    public School getSchoolById(String schoolId) {
       

        return schoolRepository.findById(schoolId).orElse(null);
    }

}
