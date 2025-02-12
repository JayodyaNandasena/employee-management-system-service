package com.dinethbakers.hrm.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Attendance {

    @NotEmpty(message = "Employee ID cannot be empty")
    private String employeeId;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be today or in the past")
    private LocalDate date;

    @NotNull(message = "Time cannot be null")
    private LocalTime time;

    @NotNull(message = "Latitude cannot be null")
    @Digits(integer = 3, fraction = 6, message = "Latitude must be a valid coordinate")
    private Double latitude;

    @NotNull(message = "Longitude cannot be null")
    @Digits(integer = 3, fraction = 6, message = "Longitude must be a valid coordinate")
    private Double longitude;
}
