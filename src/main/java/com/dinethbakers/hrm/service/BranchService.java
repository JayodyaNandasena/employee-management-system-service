package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.Branch;

import java.util.List;

public interface BranchService {
    Branch persist(Branch branch);
    Branch getById(String id);
    Branch getByName(String name);
    List<Branch> getAll();
    Branch update(Branch branch);
    Boolean delete(String id);
    List<String> getAllNames();
}
