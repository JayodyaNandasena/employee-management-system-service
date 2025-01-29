package com.dinethbakers.hrm.repository.nativerepository;

import com.dinethbakers.hrm.entity.EmployeeEntity;

public interface EmployeeNativeRepository{

    EmployeeEntity editEmployee(EmployeeEntity updatedEmployee);
}
