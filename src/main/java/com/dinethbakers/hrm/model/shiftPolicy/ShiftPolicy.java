package com.dinethbakers.hrm.model.shiftPolicy;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ShiftPolicy {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime totalHours;
}
