package com.mdms.mdms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mdms.mdms.entites.School;


@Repository
public interface SchoolRepository extends JpaRepository<School, String> {
    

    @Query("SELECT s FROM School s WHERE s.school_Email = :school_Email")
    Optional<School>findBySchoolEmail(@Param("school_Email")String school_Email);


    @Query("SELECT s FROM School s WHERE s.school_Email = :school_Email")
    public Object findBySchool_Email(@Param("school_Email")String emailOfLoggedInUser);


}
