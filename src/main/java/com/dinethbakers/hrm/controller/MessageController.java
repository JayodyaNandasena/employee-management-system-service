package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.aop.annotations.RequireRole;
import com.dinethbakers.hrm.model.Message;
import com.dinethbakers.hrm.model.OverTimeRequestRead;
import com.dinethbakers.hrm.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dinethbakers.hrm.util.RoleEnum.*;

// TODO: create get methods to view messages received by employees by id, department, branch, all etc.
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/messages")
@RequireRole(roles = {USER, DEPARTMENT_MANAGER, BRANCH_MANAGER, SUPER_ADMIN})
public class MessageController {
    private final MessageService messageService;
    @GetMapping
    public List<Message> getAll(@RequestParam String employeeId){
        return messageService.getAllByEmployee(employeeId);
    }
}
