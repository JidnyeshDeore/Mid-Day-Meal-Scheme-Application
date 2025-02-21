package com.mdms.mdms.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mdms.mdms.entites.School;
import com.mdms.mdms.repositories.SchoolRepository;

@Service
public class SecurityCustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SchoolRepository schoolRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        //database se school_email fetch karna..
        School school=schoolRepository.findBySchoolEmail(username)
            .orElseThrow(()->new UsernameNotFoundException("School with this Email Not Registerd.."));

           return school; 
    }

}
