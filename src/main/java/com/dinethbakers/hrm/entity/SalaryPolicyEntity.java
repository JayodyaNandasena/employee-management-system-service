package com.dinethbakers.hrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "salary_policy")
@Data
public class SalaryPolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "policy_id")
    private Integer policyId;

    @Column(precision = 10, scale = 2)
    private BigDecimal monthlyBasicSalary;

    @Column(precision = 10, scale = 2)
    private BigDecimal overtimeSalaryPerHour;

    @Column(precision = 4, scale = 2)
    private BigDecimal epfPercentage;

    @Column(precision = 4, scale = 2)
    private BigDecimal etfPercentage;

    @JsonIgnore
    @OneToMany(mappedBy = "salaryPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobRoleEntity> jobRoles;




}
