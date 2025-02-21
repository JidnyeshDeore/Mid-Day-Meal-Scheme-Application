package com.mdms.mdms.service;

import java.util.List;


import com.mdms.mdms.entites.School;


public interface SchoolService {

    School saveSchool(School school);

    School getSchoolByEmail(String emailOfLoggedInUser);

    public List<School> getAllSchools();

    public School getSchoolById(String schoolId);
}
