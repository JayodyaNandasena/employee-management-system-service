package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.model.Salary;

public interface SalaryService {
    Salary getSalarySlip(String employeeId);
}
