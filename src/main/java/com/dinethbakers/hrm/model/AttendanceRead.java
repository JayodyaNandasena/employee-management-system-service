package com.dinethbakers.hrm.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceRead {
    private String employeeName;
    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private LocalTime timeSpent;
}
