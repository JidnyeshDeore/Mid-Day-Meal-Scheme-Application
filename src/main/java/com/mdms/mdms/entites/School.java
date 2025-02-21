package com.mdms.mdms.entites;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "school_table")
@Data
public class School implements UserDetails{

    @Id
    private String school_Id;

    private String school_Name;

    private String school_Address;

    @Column(name = "school_Email",unique = true,nullable = false)
    private String school_Email;

    private String school_Phone_Number;

    @Getter(value = AccessLevel.NONE)
    private String school_Password;

    private String school_mdm_Instructor;

    @Column(unique = true,nullable = false)
    private String mdm_Id;//goverment id

    private int school_Strength;

    private boolean mdm_enrollment;

    private boolean school_EmailVerified=true;

    private boolean enabled=true;

   

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {

        return this.school_Password;
    }

    @Override
    public String getUsername() {
       
        return this.school_Email;
    }

}   