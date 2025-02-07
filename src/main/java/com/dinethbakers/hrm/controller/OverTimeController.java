package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.OverTimeApproval;
import com.dinethbakers.hrm.model.OverTimeRequest;
import com.dinethbakers.hrm.model.OverTimeRequestRead;
import com.dinethbakers.hrm.service.OverTimeService;
import com.dinethbakers.hrm.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/overtime")
@PreAuthorize("hasAnyRole('DEPARTMENT_MANAGER', 'BRANCH_MANAGER', 'SUPER_ADMIN')")
public class OverTimeController {
    private final OverTimeService overTimeService;

    @GetMapping
    public List<OverTimeRequestRead> getAll(@RequestParam String requestorId){
        return overTimeService.getAll(requestorId);
    }
    @GetMapping("/byStatus")
    public List<OverTimeRequestRead> getAllByStatus(
            @RequestParam StatusEnum statusEnum,
            @RequestParam String requestorId){
        return overTimeService.getAllByStatus(requestorId, statusEnum);
    }

    @GetMapping("/byId")
    public OverTimeRequestRead getById(@RequestParam String requestId){
        return overTimeService.getById(requestId);
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('USER', 'SUPER_ADMIN')")
    public ResponseEntity<Map<String, Object>> persistRequest(@RequestBody OverTimeRequest dto){
        return overTimeService.persistRequest(dto);
    }

    @PutMapping()
    public ResponseEntity<Map<String, Object>> manageRequest(@RequestBody OverTimeApproval dto){
        return overTimeService.manageRequest(dto);
    }
}
