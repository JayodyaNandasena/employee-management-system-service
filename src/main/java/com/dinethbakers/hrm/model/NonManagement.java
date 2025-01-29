package com.dinethbakers.hrm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NonManagement extends EmployeeCreate {
    private Management manager;
}
