package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.employee.EmployeeCreate;
import com.dinethbakers.hrm.model.employee.EmployeeRead;
import com.dinethbakers.hrm.model.employee.EmployeeUpdate;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmployeeService {
    EmployeeRead persist(EmployeeCreate dto);
    EmployeeCreate update(EmployeeCreate dto);
    EmployeeCreate updateProfile(EmployeeUpdate dto);
    ResponseEntity<EmployeeCreate> getById(String id);
    ResponseEntity<Map<String,String>> getNameById(String id);
}
