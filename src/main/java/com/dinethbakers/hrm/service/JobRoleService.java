package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.JobRole;

import java.util.List;
import java.util.Map;

public interface JobRoleService {
    JobRole persist (JobRole dto);

    List<JobRole> getAll();
    List<String> getAllTitles();
}
