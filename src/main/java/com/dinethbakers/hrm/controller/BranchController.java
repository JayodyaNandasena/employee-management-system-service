package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.aop.annotations.RequireRole;
import com.dinethbakers.hrm.model.Branch;
import com.dinethbakers.hrm.service.BranchService;
import com.dinethbakers.hrm.util.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.dinethbakers.hrm.util.RoleEnum.*;
import static com.dinethbakers.hrm.util.RoleEnum.SUPER_ADMIN;

@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/branch")
@RestController
@RequireRole(roles = {SUPER_ADMIN})
public class BranchController {
    private final BranchService service;

    @PostMapping
    Branch persist(@RequestBody Branch branch){
        return service.persist(branch);
    }

    @GetMapping("/all")
    List<Branch> getAll(){
        return service.getAll();
    }

    @GetMapping("/all-names")
    @RequireRole(roles = {USER, DEPARTMENT_MANAGER, BRANCH_MANAGER})
    List<String> getAllNames(){
        return service.getAllNames();
    }

    @GetMapping("/by-id")
    Branch getById(@RequestParam(name = "branchId") Integer id){
        return service.getById(id);
    }

    @GetMapping("/by-name")
    Branch getByName(@RequestParam(name = "branchName") String name){
        return service.getByName(name);
    }

    @PutMapping
    Branch update(@RequestBody Branch branch){
        return service.update(branch);
    }

    @DeleteMapping
    Map<String, Boolean> deleteById(@RequestParam(name = "branchId") Integer id){
        return Collections.singletonMap("Delete status", service.delete(id));
    }

}
