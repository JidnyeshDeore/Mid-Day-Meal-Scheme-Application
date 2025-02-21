package com.mdms.mdms.service;

import java.util.List;

import com.mdms.mdms.entites.MealRequest;

public interface MealRequestService {

    public MealRequest createMealRequest(String schoolId, MealRequest mealRequest);

    public List<MealRequest> viewMealRequests();

    public MealRequest getSpecificMealRequest(String mealRequestId);

    public void save(MealRequest mealRequest);

    public List<MealRequest> getPendingOrApprovedRequests();

    public List<MealRequest> getMealRequestBySchoolId(String schoolId);

    public void updateMealRequestStatus(String schoolId, String mealRequestId, MealRequest.Status status);
}
