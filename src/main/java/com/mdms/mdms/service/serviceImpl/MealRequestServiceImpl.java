package com.mdms.mdms.service.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdms.mdms.entites.MealRequest;
import com.mdms.mdms.entites.School;
import com.mdms.mdms.repositories.MealRequestRepository;
import com.mdms.mdms.repositories.SchoolRepository;
import com.mdms.mdms.service.MealRequestService;


@Service
public class MealRequestServiceImpl implements MealRequestService{


    @Autowired
    private MealRequestRepository mealRequestRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public MealRequest createMealRequest(String schoolId,MealRequest mealRequest) {

        String mealId=UUID.randomUUID().toString().substring(0, 10);
        mealRequest.setMealRequestId(mealId);
        School school = schoolRepository.findById(schoolId)
                    .orElseThrow(() -> new RuntimeException("School not found"));

            mealRequest.setSchool(school); // Link the meal request to the school

            return mealRequestRepository.save(mealRequest); // Save the meal request to DB
    }

    @Override
    public List<MealRequest> viewMealRequests() {
        
        // List<MealRequest>pendingMealRequests = new ArrayList<>();
        List<MealRequest>allMealRequests=mealRequestRepository.findAll();
        // System.out.println(allMealRequests);
        // for(MealRequest request:allMealRequests){
        //     if(request.getStatus()==Status.PENDING){
        //         //Instead of returning all this information only return important one by creating DTO----refer ###1
        //         pendingMealRequests.add(request);
        //     }
        // }
        // System.out.println(pendingMealRequests);
        return allMealRequests;
    }

    @Override
    public MealRequest getSpecificMealRequest(String mealRequestId) {
        return mealRequestRepository.findById(mealRequestId).orElse(null);
    }

    @Override
    public void save(MealRequest mealRequest) {
        
        mealRequestRepository.save(mealRequest);
    }

    @Override
    public List<MealRequest> getPendingOrApprovedRequests() {
        return mealRequestRepository.findByStatusIn(List.of(MealRequest.Status.PENDING, MealRequest.Status.APPROVED));
    }

    @Override
    public List<MealRequest> getMealRequestBySchoolId(String schoolId) {
        
      List<MealRequest>requests=  mealRequestRepository.findBySchool_Id(schoolId);
      return requests;
    }
    
    @Override
    public void updateMealRequestStatus(String schoolId, String mealRequestId, MealRequest.Status status) {
        MealRequest mealRequest = mealRequestRepository.findByMealRequestIdAndSchool_School_Id(mealRequestId, schoolId)
            .orElseThrow(() -> new IllegalArgumentException("Meal request not found for this school"));

        mealRequest.setStatus(status);
        mealRequestRepository.save(mealRequest);
    }



}

// // ###1
// public class MealRequestDTO {
//     private String mealRequestId;
//     private String schoolName;
//     private String schoolAddress;
//     private String schoolEmail;
//     private String mealType;
//     private double quantity;
//     private String nutritionalRequirements;
//     private String deliveryDate;
//     private String contactPerson;
//     private String contactPhone;
//     private String status;

//     // Add constructors, getters, and setters
// }


// @Override
// public List<MealRequestDTO> viewMealRequests() {
//     List<MealRequestDTO> pendingMealRequests = new ArrayList<>();
    
//     // Retrieve all meal requests
//     List<MealRequest> allMealRequests = mealRequestRepository.findAll();
    
//     // Filter and map to DTO
//     for (MealRequest request : allMealRequests) {
//         if (request.getStatus() == Status.PENDING) {
//             MealRequestDTO dto = new MealRequestDTO();
//             dto.setMealRequestId(request.getMealRequestId());
//             dto.setSchoolName(request.getSchool().getSchool_Name());
//             dto.setSchoolAddress(request.getSchool().getSchool_Address());
//             dto.setSchoolEmail(request.getSchool().getSchool_Email());
//             dto.setMealType(request.getMealType());
//             dto.setQuantity(request.getQuantity());
//             dto.setNutritionalRequirements(request.getNutritionalRequirements());
//             dto.setDeliveryDate(request.getDeliveryDate());
//             dto.setContactPerson(request.getContactPerson());
//             dto.setContactPhone(request.getContactPhone());
//             dto.setStatus(request.getStatus().toString());
            
//             pendingMealRequests.add(dto);
//         }
//     }
    
//     return pendingMealRequests;
// }
