package com.dinethbakers.hrm.model.jobRole;

import com.dinethbakers.hrm.model.shiftPolicy.ShiftPolicy;
import com.dinethbakers.hrm.model.leavePolicy.LeavePolicy;
import com.dinethbakers.hrm.model.salary.SalaryPolicy;
import com.dinethbakers.hrm.util.ShiftTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class JobRole {

    @NotBlank(message = "Job role title cannot be empty")
    private String title;

    @NotNull(message = "Leave policy cannot be null")
    @Valid
    private LeavePolicy leavePolicy;

    @NotNull(message = "Salary policy cannot be null")
    @Valid
    private SalaryPolicy salaryPolicy;

    @NotNull(message = "Shift type must be specified")
    private ShiftTypeEnum shiftTypeEnum;

    @NotNull(message = "Shift policies cannot be null")
    @Size(min = 1, message = "At least one shift policy must be provided")
    @Valid
    private List<ShiftPolicy> shiftPolicies;
}
