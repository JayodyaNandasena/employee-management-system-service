package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.aop.annotations.RequireRole;
import com.dinethbakers.hrm.model.TimeOffApproval;
import com.dinethbakers.hrm.model.TimeOffRequest;
import com.dinethbakers.hrm.model.TimeOffRequestRead;
import com.dinethbakers.hrm.service.TimeOffService;
import com.dinethbakers.hrm.util.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.dinethbakers.hrm.util.RoleEnum.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/timeOff")
@RequireRole(roles = {SUPER_ADMIN})
public class TimeOffController {
    private final TimeOffService timeOffService;

    @GetMapping
    @RequireRole(roles = {DEPARTMENT_MANAGER, BRANCH_MANAGER})
    public List<TimeOffRequestRead> getAll(@RequestParam String requestorId){
        return timeOffService.getAll(requestorId);
    }
    @GetMapping("/byStatus")
    @RequireRole(roles = {DEPARTMENT_MANAGER, BRANCH_MANAGER})
    public List<TimeOffRequestRead> getAllByStatus(
            @RequestParam StatusEnum statusEnum,
            @RequestParam String requestorId){
        return timeOffService.getAllByStatus(requestorId, statusEnum);
    }

    @GetMapping("/byId")
    @RequireRole(roles = {DEPARTMENT_MANAGER, BRANCH_MANAGER})
    public TimeOffRequestRead getById(@RequestParam String requestId){
        return timeOffService.getById(requestId);
    }

    @PostMapping()
    @RequireRole(roles = {USER})
    public ResponseEntity<Map<String, Object>> persistRequest(@RequestBody TimeOffRequest dto){
        return timeOffService.persistRequest(dto);
    }

    @PutMapping()
    @RequireRole(roles = {DEPARTMENT_MANAGER, BRANCH_MANAGER})
    public ResponseEntity<Map<String, Object>> manageRequest(@RequestBody TimeOffApproval dto){
        return timeOffService.manageRequest(dto);
    }
}
