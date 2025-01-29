package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.Message;

import java.util.List;

public interface MessageService {
    List<Message> getAllByEmployee(String employeeId);
}
