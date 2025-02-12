package com.dinethbakers.hrm.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Message {
    private Integer messageId;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Time cannot be null")
    private LocalTime time;

    @NotBlank(message = "Text cannot be empty")
    private String text;
}
