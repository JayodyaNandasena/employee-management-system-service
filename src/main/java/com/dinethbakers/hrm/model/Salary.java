package com.dinethbakers.hrm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Salary {
    private BigDecimal basicSalary;
    private BigDecimal epfPercentage;
    private BigDecimal epfAmount;
    private BigDecimal etfPercentage;
    private BigDecimal etfAmount;
    private BigDecimal otHours;
    private BigDecimal otPerHour;
    private BigDecimal grossOTIncome;
    private BigDecimal grossEarnings;
    private BigDecimal grossDeductions;
    private BigDecimal grossSalary;

}
