package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.aop.annotations.RequireRole;
import com.dinethbakers.hrm.model.Salary;
import com.dinethbakers.hrm.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.dinethbakers.hrm.util.RoleEnum.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/salary")
@RequireRole(roles = {USER, DEPARTMENT_MANAGER, BRANCH_MANAGER, SUPER_ADMIN})
public class SalaryController {
    private final SalaryService salaryService;
    @PostMapping
    public Salary getSalary(@RequestParam String employeeId){
        return salaryService.getSalarySlip(employeeId);
    }
}
