package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.timeOff.TimeOffApproval;
import com.dinethbakers.hrm.model.timeOff.TimeOffRequest;
import com.dinethbakers.hrm.model.timeOff.TimeOffRequestRead;
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
