package com.dinethbakers.hrm.service.impl;

import com.dinethbakers.hrm.entity.EmployeeEntity;
import com.dinethbakers.hrm.entity.JobRoleEntity;
import com.dinethbakers.hrm.entity.SalaryPolicyEntity;
import com.dinethbakers.hrm.model.Salary;
import com.dinethbakers.hrm.repository.jparepository.EmployeeRepository;
import com.dinethbakers.hrm.repository.jparepository.OverTimeRepository;
import com.dinethbakers.hrm.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    private final EmployeeRepository employeeRepository;
    private final OverTimeRepository overTimeRepository;
    @Override
    public Salary getSalarySlip(String employeeId) {
        Optional<EmployeeEntity> byId = employeeRepository.findById(employeeId);

        if (byId.isEmpty()) {
            return null;
        }

        EmployeeEntity employee = byId.get();
        JobRoleEntity jobRole = employee.getJobRole();

        if (jobRole == null || jobRole.getSalaryPolicy() == null) {
            return null;
        }

        SalaryPolicyEntity salaryPolicy = jobRole.getSalaryPolicy();
        BigDecimal basicSalary = salaryPolicy.getMonthlyBasicSalary();
        BigDecimal epfPercentage = salaryPolicy.getEpfPercentage();
        BigDecimal etfPercentage = salaryPolicy.getEtfPercentage();

        BigDecimal epfSalary = calculatePercentage(basicSalary, epfPercentage);
        BigDecimal etfSalary = calculatePercentage(basicSalary, etfPercentage);
        BigDecimal totalDeductions = epfSalary.add(etfSalary);

        BigDecimal otPerHour = salaryPolicy.getOvertimeSalaryPerHour();
        BigDecimal otPayment = BigDecimal.ZERO;
        BigDecimal otHours = BigDecimal.ZERO;

        if (otPerHour.compareTo(BigDecimal.ZERO) != 0) {
            otPayment = overTimeRepository.findTotalOvertimePaymentByEmployeeId(employeeId);
            if (otPayment != null) {
                otHours = otPayment.divide(otPerHour, 2, RoundingMode.HALF_UP);
            }
        }

        BigDecimal grossSalary = basicSalary.add(otPayment);
        BigDecimal netSalary = grossSalary.subtract(totalDeductions);

        return new Salary(
                basicSalary,
                epfPercentage,
                epfSalary,
                etfPercentage,
                etfSalary,
                otHours,
                otPerHour,
                otPayment,
                grossSalary,
                totalDeductions,
                netSalary
        );
    }

    private BigDecimal calculatePercentage(BigDecimal base, BigDecimal percentage) {
        return base.multiply(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

}
