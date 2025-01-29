package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.Attendance;
import com.dinethbakers.hrm.model.AttendanceRead;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AttendanceService {
    ResponseEntity<Map<String, Object>> markClockIn(Attendance dto);
    ResponseEntity<Map<String, Object>> markClockOut(Attendance dto);
    List<AttendanceRead> recordsByEmployee(String employeeId);
}
