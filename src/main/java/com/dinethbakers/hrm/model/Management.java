package com.dinethbakers.hrm.model;

import com.dinethbakers.hrm.model.employee.EmployeeCreate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Management extends EmployeeCreate {
    private String managementLevel;
}
