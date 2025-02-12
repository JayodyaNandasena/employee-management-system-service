package com.dinethbakers.hrm.model;

import com.dinethbakers.hrm.util.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OverTimeApproval {

    @NotBlank(message = "Manager ID cannot be empty")
    private String managerId;

    @NotBlank(message = "Request ID cannot be empty")
    private String requestId;

    @NotNull(message = "Status cannot be null")
    private StatusEnum statusEnum;

    @NotNull(message = "Approved date-time cannot be null")
    @PastOrPresent(message = "Approved date-time must be in the past or present")
    private LocalDateTime approvedDateTime;
}
