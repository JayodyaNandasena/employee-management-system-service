package com.dinethbakers.hrm.model.account;

import com.dinethbakers.hrm.model.employee.EmployeeRead;
import lombok.Data;

@Data
public class AccountRead {
    private Boolean isManager;
    private EmployeeRead employee;
}
