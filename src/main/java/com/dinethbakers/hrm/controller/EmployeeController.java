package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.EmployeeCreate;
import com.dinethbakers.hrm.model.response.SuccessResponse;
import com.dinethbakers.hrm.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/employee")
@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    // TODO: create separate create methods for dep manager, branch manager and admin
    @PostMapping
    @PreAuthorize("hasAnyRole('DEPARTMENT_MANAGER', 'BRANCH_MANAGER', 'SUPER_ADMIN')")
    public ResponseEntity<SuccessResponse> persist(@Valid @RequestBody EmployeeCreate dto){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(employeeService.persist(dto))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    // TODO: create seperate update methods for user, dep manager and branch manager
    @PutMapping
    @PreAuthorize("hasAnyRole('USER', 'DEPARTMENT_MANAGER', 'BRANCH_MANAGER', 'SUPER_ADMIN')")
    public EmployeeCreate update(@Valid @RequestBody EmployeeCreate dto){
        return employeeService.update(dto);
    }

    @GetMapping("/by-id")
    @PreAuthorize("hasAnyRole('DEPARTMENT_MANAGER', 'BRANCH_MANAGER', 'SUPER_ADMIN')")
    public ResponseEntity<EmployeeCreate> getById(@RequestParam String id){
        return employeeService.getById(id);
    }
}
