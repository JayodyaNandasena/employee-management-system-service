package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.TimeOffApproval;
import com.dinethbakers.hrm.model.TimeOffRequest;
import com.dinethbakers.hrm.model.TimeOffRequestRead;
import com.dinethbakers.hrm.service.TimeOffService;
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
@RequestMapping("/timeOff")
@PreAuthorize("hasAnyRole('DEPARTMENT_MANAGER', 'BRANCH_MANAGER', 'SUPER_ADMIN')")public class TimeOffController {
    private final TimeOffService timeOffService;

    @GetMapping
    public List<TimeOffRequestRead> getAll(@RequestParam String requestorId){
        return timeOffService.getAll(requestorId);
    }
    @GetMapping("/byStatus")
    public List<TimeOffRequestRead> getAllByStatus(
            @RequestParam StatusEnum statusEnum,
            @RequestParam String requestorId){
        return timeOffService.getAllByStatus(requestorId, statusEnum);
    }

    @GetMapping("/byId")
    public TimeOffRequestRead getById(@RequestParam String requestId){
        return timeOffService.getById(requestId);
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('USER', 'SUPER_ADMIN')")
    public ResponseEntity<Map<String, Object>> persistRequest(@RequestBody TimeOffRequest dto){
        return timeOffService.persistRequest(dto);
    }

    @PutMapping()
    public ResponseEntity<Map<String, Object>> manageRequest(@RequestBody TimeOffApproval dto){
        return timeOffService.manageRequest(dto);
    }
}
