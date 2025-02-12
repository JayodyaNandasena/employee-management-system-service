package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.Branch;
import com.dinethbakers.hrm.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/branch")
@RestController
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class BranchController {
    private final BranchService service;

    @PostMapping
    Branch persist(@Valid @RequestBody Branch branch){
        return service.persist(branch);
    }

    @GetMapping("/all")
    List<Branch> getAll(){
        return service.getAll();
    }

    @GetMapping("/all-names")
    @PreAuthorize("hasAnyRole('USER', 'DEPARTMENT_MANAGER', 'BRANCH_MANAGER', 'SUPER_ADMIN')")
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
    Branch update(@Valid @RequestBody Branch branch){
        return service.update(branch);
    }

    @DeleteMapping
    Map<String, Boolean> deleteById(@RequestParam(name = "branchId") Integer id){
        return Collections.singletonMap("Delete status", service.delete(id));
    }

}
