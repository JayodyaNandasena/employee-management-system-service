package com.dinethbakers.hrm.repository.nativerepository;

import com.dinethbakers.hrm.entity.EmployeeEntity;

public interface EmployeeNativeRepository{

    EmployeeEntity editEmployee(EmployeeEntity updatedEmployee);
    EmployeeEntity editProfile(EmployeeEntity updatedEmployee);
    String nameById(String id);
}
