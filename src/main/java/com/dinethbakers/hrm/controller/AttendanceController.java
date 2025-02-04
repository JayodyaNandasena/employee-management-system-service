package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.model.Attendance;
import com.dinethbakers.hrm.model.AttendanceRead;
import com.dinethbakers.hrm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/clockIn")
    public ResponseEntity<Map<String, Object>> markClockIn(@RequestBody Attendance dto){
        return attendanceService.markClockIn(dto);
    }

    @PostMapping("/clockOut")
    public ResponseEntity<Map<String, Object>> markClockOut(@RequestBody Attendance dto){
        return attendanceService.markClockOut(dto);
    }

    @GetMapping
    public List<AttendanceRead> getAllByEmployee(@RequestParam String employeeId){
        return attendanceService.recordsByEmployee(employeeId);
    }
}
