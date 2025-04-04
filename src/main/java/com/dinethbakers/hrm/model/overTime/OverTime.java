package com.dinethbakers.hrm.model;

import com.dinethbakers.hrm.util.StatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class OverTime {
    private String employeeId;
    private LocalDate requestDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Double paymentAmount;
    private StatusEnum statusEnum;
    private LocalDate approvedDateTime;
}