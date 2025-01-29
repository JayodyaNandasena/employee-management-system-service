package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.*;
import com.dinethbakers.hrm.util.StatusEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface OverTimeService {
    ResponseEntity<Map<String, Object>> persistRequest(OverTimeRequest dto);
    ResponseEntity<Map<String, Object>> manageRequest(OverTimeApproval dto);
    List<OverTimeRequestRead> getAllByStatus(String requesterId, StatusEnum statusEnum);
    List<OverTimeRequestRead> getAll(String requesterId);
    OverTimeRequestRead getById(String requestId);
}
