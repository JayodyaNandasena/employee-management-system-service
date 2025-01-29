package com.dinethbakers.hrm.service.impl;

import com.dinethbakers.hrm.entity.AttendanceEntity;
import com.dinethbakers.hrm.entity.BranchEntity;
import com.dinethbakers.hrm.entity.EmployeeEntity;
import com.dinethbakers.hrm.model.Attendance;
import com.dinethbakers.hrm.model.AttendanceRead;
import com.dinethbakers.hrm.repository.jparepository.AttendanceRepository;
import com.dinethbakers.hrm.repository.jparepository.EmployeeRepository;
import com.dinethbakers.hrm.service.AttendanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper mapper;

    @Override
    public ResponseEntity<Map<String, Object>> markClockIn(Attendance dto) {
        Map<String, Object> result = new HashMap<>();
        String key1 = "status";
        String key2 = "message";

        if (!attendanceRepository
                .existsByEmployeeIdAndTimeInAndDate(
                        dto.getEmployeeId(),
                        dto.getDate()
                )) {
            if (Boolean.TRUE.equals(matchLocations(dto.getEmployeeId(), dto.getLatitude(), dto.getLongitude()))) {
                AttendanceEntity entity = new AttendanceEntity();
                entity.setDate(dto.getDate());
                entity.setTimeIn(dto.getTime());

                Optional<EmployeeEntity> employeeById = employeeRepository.findById(dto.getEmployeeId());

                if (employeeById.isPresent()) {
                    entity.setEmployee(employeeById.get());
                    attendanceRepository.save(entity);
                    result.put(key1, true);
                    result.put(key2, "Clock in recorded successfully.");
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    result.put(key1, false);
                    result.put(key2, "Employee not found.");
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                }
            } else {
                result.put(key1, false);
                result.put(key2, "Location mismatch: You must clock in within 1 km of your branch.");
                return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
            }
        }
        result.put(key1, false);
        result.put(key2, "You have already clocked in for today.");
        return new ResponseEntity<>(result, HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<Map<String, Object>> markClockOut(Attendance dto) {
        Map<String, Object> result = new HashMap<>();
        String key1 = "status";
        String key2 = "message";

        Integer attendanceId = attendanceRepository.findIdByEmployeeIdAndDate(
                dto.getEmployeeId(),
                dto.getDate());

        if (attendanceId == null) {
            result.put(key1, false);
            result.put(key2, "Attendance record not found.");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        if (Boolean.TRUE.equals(matchLocations(dto.getEmployeeId(), dto.getLatitude(), dto.getLongitude()))) {
            attendanceRepository.updateTimeOutById(attendanceId, dto.getTime());
            result.put(key1, true);
            result.put(key2, "Clock out time recorded successfully.");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        result.put(key1, false);
        result.put(key2, "Location mismatch: You must clock in within 1 km of your branch.");
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }

    @Override
    public List<AttendanceRead> recordsByEmployee(String employeeId) {
        List<AttendanceRead> attendanceRecords = new ArrayList<>();

        Optional<EmployeeEntity> employeeById = employeeRepository.findById(employeeId);

        if (employeeById.isEmpty()){
            return Collections.emptyList();
        }

        for (AttendanceEntity entity : attendanceRepository.findByEmployeeOrderByDateDesc(employeeById.get())) {
            AttendanceRead attendanceRead = mapper.convertValue(entity, AttendanceRead.class);
            attendanceRead.setEmployeeName(
                    entity.getEmployee().getFirstName() + " " + entity.getEmployee().getLastName()
            );
            attendanceRead.setTimeSpent(calculateTimeSpent(
                    attendanceRead.getTimeIn(),
                    attendanceRead.getTimeOut()
            ));
            attendanceRecords.add(attendanceRead);
        }
        return attendanceRecords;
    }

    private Boolean matchLocations(String employeeId,
                                   Double currentLatitude,
                                   Double currentLongitude) {
        BranchEntity branchByEmployeeId =
                employeeRepository.findBranchByEmployeeId(employeeId);

        double branchLatitude = branchByEmployeeId.getLatitude().doubleValue();
        double branchLongitude = branchByEmployeeId.getLongitude().doubleValue();

        double distance = calculateDistance(
                branchLatitude, branchLongitude,
                currentLatitude, currentLongitude);

        // Check if the distance is within 1 km
        return distance <= 1.0;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the Earth in km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    public LocalTime calculateTimeSpent(LocalTime start, LocalTime end) {
        if (end == null){
            return null;
        }
        // Ensure end is after start
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        // Calculate the duration between start and end
        Duration duration = Duration.between(start, end);

        // Convert duration to hours, minutes, and seconds
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        // Create a LocalTime object representing the duration
        return LocalTime.of((int) hours, (int) minutes, (int) seconds);
    }
}
