package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.SalaryPolicyEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface SalaryPolicyRepository extends CrudRepository<SalaryPolicyEntity,String> {
    Optional<SalaryPolicyEntity>
        findByMonthlyBasicSalaryAndOvertimeSalaryPerHourAndEpfPercentageAndEtfPercentage(
            BigDecimal monthlyBasicSalary, BigDecimal overtimeSalaryPerHour,
            BigDecimal epfPercentage, BigDecimal etfPercentage);
}
