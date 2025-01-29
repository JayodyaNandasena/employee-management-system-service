package com.dinethbakers.hrm.model;

import com.dinethbakers.hrm.util.ShiftTypeEnum;
import lombok.Data;
import java.util.List;

@Data
public class JobRole {
    private String title;
    private LeavePolicy leavePolicy;
    private SalaryPolicy salaryPolicy;
    private ShiftTypeEnum shiftTypeEnum;
    private List<ShiftPolicy> shiftPolicies;
}
