package com.dinethbakers.hrm.model;

import com.dinethbakers.hrm.util.StatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OverTimeApproval {
    private String managerId;
    private String requestId;
    private StatusEnum statusEnum;
    private LocalDateTime approvedDateTime;
}
