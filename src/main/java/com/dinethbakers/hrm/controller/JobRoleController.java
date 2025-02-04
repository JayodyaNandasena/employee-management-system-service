package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.aop.annotations.RequireRole;
import com.dinethbakers.hrm.model.JobRole;
import com.dinethbakers.hrm.service.JobRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.dinethbakers.hrm.util.RoleEnum.*;

@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/jobrole")
@RestController
@RequireRole(roles = {SUPER_ADMIN})
public class JobRoleController {
    private final JobRoleService jobRoleService;

    @PostMapping
    @RequireRole(roles = {DEPARTMENT_MANAGER, BRANCH_MANAGER})
    public JobRole persist(@RequestBody JobRole jobRole){
        return jobRoleService.persist(jobRole);
    }

    @GetMapping
    @RequireRole(roles = {DEPARTMENT_MANAGER, BRANCH_MANAGER})
    public List<JobRole> getAll(){return jobRoleService.getAll();}

    @GetMapping("/titles")
    @RequireRole(roles = {DEPARTMENT_MANAGER, BRANCH_MANAGER})
    public List<String> getAllTitles(){
        return jobRoleService.getAllTitles();
    }

}
