package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.Branch;
import com.dinethbakers.hrm.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/branch")
@RestController
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
    List<String> getAllNames(){
        return service.getAllNames();
    }

    @GetMapping("/by-id")
    Branch getById(@RequestParam(name = "branchId") String id){
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
    Map<String, Boolean> deleteById(@RequestParam(name = "branchId") String id){
        return Collections.singletonMap("Delete status", service.delete(id));
    }

}
