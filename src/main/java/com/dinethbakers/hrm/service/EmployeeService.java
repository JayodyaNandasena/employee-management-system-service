package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.EmployeeCreate;
import com.dinethbakers.hrm.model.EmployeeRead;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmployeeService {
    EmployeeRead persist(EmployeeCreate dto);
    EmployeeCreate update(EmployeeCreate dto);
    ResponseEntity<EmployeeCreate> getById(String id);
    ResponseEntity<Map<String,String>> getNameById(String id);
}
