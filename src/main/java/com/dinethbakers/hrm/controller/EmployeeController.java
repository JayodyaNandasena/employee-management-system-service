package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.aop.annotations.RequireRole;
import com.dinethbakers.hrm.model.EmployeeCreate;
import com.dinethbakers.hrm.model.response.SuccessResponse;
import com.dinethbakers.hrm.service.EmployeeService;
import com.dinethbakers.hrm.util.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dinethbakers.hrm.util.RoleEnum.*;

@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/employee")
@RestController
@RequireRole(roles = {SUPER_ADMIN})
public class EmployeeController {
    private final EmployeeService employeeService;

    // TODO: create separate create methods for dep manager, branch manager and admin
    @PostMapping
    @RequireRole(roles = {DEPARTMENT_MANAGER, BRANCH_MANAGER})
    public ResponseEntity<SuccessResponse> persist(@RequestBody EmployeeCreate dto){
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(employeeService.persist(dto))
                .build();
        return ResponseEntity.ok().body(successResponse);
    }

    // TODO: create seperate update methods for user, dep manager and branch manager
    @PutMapping
    @RequireRole(roles = {USER, DEPARTMENT_MANAGER, BRANCH_MANAGER})
    public EmployeeCreate update(@RequestBody EmployeeCreate dto){
        return employeeService.update(dto);
    }

    @GetMapping("/by-id")
    @RequireRole(roles = {DEPARTMENT_MANAGER, BRANCH_MANAGER})
    public ResponseEntity<EmployeeCreate> getById(@RequestParam String id){
        return employeeService.getById(id);
    }
}
