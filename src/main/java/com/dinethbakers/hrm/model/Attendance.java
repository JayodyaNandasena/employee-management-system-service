package com.dinethbakers.hrm.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Attendance {
    private String employeeId;
    private LocalDate date;
    private LocalTime time;
    private Double latitude;
    private Double longitude;
}
