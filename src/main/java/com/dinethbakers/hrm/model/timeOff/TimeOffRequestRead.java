package com.dinethbakers.hrm.model.timeOff;

import com.dinethbakers.hrm.model.employee.EmployeeCreate;
import com.dinethbakers.hrm.util.StatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeOffRequestRead {
    private EmployeeCreate employee;
    private String requestId;
    private String text;
    private LocalDateTime requestDateTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private StatusEnum statusEnum;
}
