package com.dinethbakers.hrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "salary_policy")
@Data
public class SalaryPolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(precision = 10, scale = 2)
    private BigDecimal monthlyBasicSalary;

    @Column(precision = 10, scale = 2)
    private BigDecimal overtimeSalaryPerHour;

    @Column(precision = 4, scale = 2)
    private BigDecimal epfPercentage;

    @Column(precision = 4, scale = 2)
    private BigDecimal etfPercentage;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "salaryPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobRoleEntity> jobRoles;




}
