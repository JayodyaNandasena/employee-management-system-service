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
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be less than -90 and 90")
    @Digits(integer = 2, fraction = 7, message = "Latitude must have at most 7 decimal places")
    private Double latitude;

    @NotNull(message = "Longitude cannot be null")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    @Digits(integer = 3, fraction = 7, message = "Longitude must have at most 7 decimal places")
    private Double longitude;
}
