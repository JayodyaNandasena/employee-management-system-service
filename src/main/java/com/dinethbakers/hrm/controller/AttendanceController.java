package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.Attendance;
import com.dinethbakers.hrm.model.AttendanceRead;
import com.dinethbakers.hrm.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/attendance")
@PreAuthorize("hasAnyRole('USER', 'DEPARTMENT_MANAGER', 'BRANCH_MANAGER', 'SUPER_ADMIN')")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/clockIn")
    public ResponseEntity<Map<String, Object>> markClockIn(@Valid @RequestBody Attendance dto){
        return attendanceService.markClockIn(dto);
    }

    @PostMapping("/clockOut")
    public ResponseEntity<Map<String, Object>> markClockOut(@Valid @RequestBody Attendance dto){
        return attendanceService.markClockOut(dto);
    }

    @GetMapping
    public List<AttendanceRead> getAllByEmployee(@RequestParam String employeeId){
        return attendanceService.recordsByEmployee(employeeId);
    }

    // TODO: get all by branch, department, all
}
