package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.salary.Salary;
import com.dinethbakers.hrm.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/salary")
@PreAuthorize("hasAnyRole('USER', 'DEPARTMENT_MANAGER', 'BRANCH_MANAGER', 'SUPER_ADMIN')")
public class SalaryController {
    private final SalaryService salaryService;
    @GetMapping
    public Salary getSalary(@RequestParam String employeeId){
        return salaryService.getSalarySlip(employeeId);
    }
}
