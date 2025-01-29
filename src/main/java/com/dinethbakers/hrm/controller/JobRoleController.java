package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.JobRole;
import com.dinethbakers.hrm.service.JobRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/jobrole")
@RestController
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
