package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.EmployeeCreate;
import com.dinethbakers.hrm.model.EmployeeRead;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    EmployeeRead persist(EmployeeCreate dto);
    EmployeeCreate update(EmployeeCreate dto);
    ResponseEntity<EmployeeCreate> getById(String id);
}
