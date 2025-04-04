package com.dinethbakers.hrm.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeOffRequest {

    @NotBlank(message = "Employee ID cannot be empty")
    private String employeeId;

    @NotBlank(message = "Text cannot be empty")
    private String text;

    @NotNull(message = "Request date-time cannot be null")
    @PastOrPresent(message = "Request date-time must be in the past or present")
    private LocalDateTime requestDateTime;

    @NotNull(message = "Start date-time cannot be null")
    @FutureOrPresent(message = "Start date-time must be in the future or present")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date-time cannot be null")
    @FutureOrPresent(message = "End date-time must be in the future or present")
    private LocalDateTime endDateTime;
}
