package com.dinethbakers.hrm.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LeavePolicy {
    @NotNull(message = "Number of PTO days cannot be null")
    @Min(value = 1, message = "Number of PTO days must be at least 1")
    private Integer noOfPTODays;
}
