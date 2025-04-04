package com.dinethbakers.hrm.model;

import lombok.Data;

@Data
public class AccountRead {
    private Boolean isManager;
    private EmployeeRead employee;
}
