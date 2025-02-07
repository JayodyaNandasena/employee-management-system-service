package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.JobRole;
import com.dinethbakers.hrm.service.JobRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/jobrole")
@RestController
@PreAuthorize("hasAnyRole('DEPARTMENT_MANAGER', 'BRANCH_MANAGER', 'SUPER_ADMIN')")
public class JobRoleController {
    private final JobRoleService jobRoleService;

    @PostMapping
    public JobRole persist(@RequestBody JobRole jobRole){
        return jobRoleService.persist(jobRole);
    }

    @GetMapping
    public List<JobRole> getAll(){return jobRoleService.getAll();}

    @GetMapping("/titles")
    public List<String> getAllTitles(){
        return jobRoleService.getAllTitles();
    }

}
