package com.mdms.mdms.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mdms.mdms.entites.Supplier;
import com.mdms.mdms.repositories.SupplierRepository;

@Service
public class SupplierUserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        Supplier supplier=supplierRepository.findBySupplierEmail(username)
        .orElseThrow(()->new UsernameNotFoundException("Supplier with this email not found.."));
        
        return supplier;
    }

}
