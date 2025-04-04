package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.jobRole.JobRole;

import java.util.List;

public interface JobRoleService {
    JobRole persist (JobRole dto);
    List<JobRole> getAll();
    List<String> getAllTitles();
}
