package com.dinethbakers.hrm.model;

import com.dinethbakers.hrm.util.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class OverTimeRequest {

    @NotBlank(message = "Employee ID cannot be empty")
    private String employeeId;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Request date cannot be null")
    @PastOrPresent(message = "Request date must be in the past or present")
    private LocalDateTime requestDate;

    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;

    private String text;

    private StatusEnum statusEnum;
}
