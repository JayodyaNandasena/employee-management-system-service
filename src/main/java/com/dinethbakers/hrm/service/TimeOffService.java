package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.TimeOffApproval;
import com.dinethbakers.hrm.model.TimeOffRequest;
import com.dinethbakers.hrm.model.TimeOffRequestRead;
import com.dinethbakers.hrm.util.StatusEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TimeOffService {
    ResponseEntity<Map<String, Object>> persistRequest(TimeOffRequest dto);
    ResponseEntity<Map<String, Object>> manageRequest(TimeOffApproval dto);

    List<TimeOffRequestRead> getAllByStatus(String requesterId, StatusEnum statusEnum);
    List<TimeOffRequestRead> getAll(String requesterId);

    TimeOffRequestRead getById(String requestId);
}
