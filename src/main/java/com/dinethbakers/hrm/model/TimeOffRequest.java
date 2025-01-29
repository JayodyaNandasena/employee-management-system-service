package com.dinethbakers.hrm.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeOffRequest {
    private String employeeId;
    private String text;
    private LocalDateTime requestDateTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
