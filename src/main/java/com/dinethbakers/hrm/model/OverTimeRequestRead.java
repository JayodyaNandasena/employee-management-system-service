package com.dinethbakers.hrm.model;

import com.dinethbakers.hrm.util.StatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class OverTimeRequestRead {
    private String requestId;
    private EmployeeCreate employee;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String text;
    private StatusEnum statusEnum;
}
