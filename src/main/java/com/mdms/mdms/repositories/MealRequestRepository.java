package com.mdms.mdms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mdms.mdms.entites.MealRequest;

@Repository
public interface MealRequestRepository extends JpaRepository<MealRequest, String>{

     // Custom query to find meal requests for a specific school based on the school_Id
    @Query("SELECT mr FROM MealRequest mr WHERE mr.school.school_Id = :schoolId")
    List<MealRequest> findBySchool_Id(String schoolId); // Find meal requests for a specific school


    @Query("SELECT mr FROM MealRequest mr WHERE mr.status IN :statuses")
    List<MealRequest> findByStatusIn(@Param("statuses") List<MealRequest.Status> statuses);

    @Query("SELECT mr FROM MealRequest mr WHERE mr.mealRequestId = :mealRequestId AND mr.school.school_Id = :schoolId")
    Optional<MealRequest> findByMealRequestIdAndSchool_School_Id(
        @Param("mealRequestId") String mealRequestId, 
        @Param("schoolId") String schoolId
    );
}
