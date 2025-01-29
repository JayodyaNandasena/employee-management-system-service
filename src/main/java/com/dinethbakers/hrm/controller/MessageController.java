package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.Message;
import com.dinethbakers.hrm.model.OverTimeRequestRead;
import com.dinethbakers.hrm.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    @GetMapping
    public List<Message> getAll(@RequestParam String employeeId){
        return messageService.getAllByEmployee(employeeId);
    }
}
