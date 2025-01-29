package com.dinethbakers.hrm.entity;

import com.dinethbakers.hrm.util.ShiftTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "job_role")
@Data
public class JobRoleEntity {
    @Id
    @Column(name = "job_role_id")
    private String jobRoleId;

    @Column(unique = true)
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "jobRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeeEntity> employees;

    @ManyToOne
    @JoinColumn(name = "leave_policy", referencedColumnName = "policy_id")
    private LeavePolicyEntity leavePolicy;

    @ManyToOne
    @JoinColumn(name = "salary_policy", referencedColumnName = "policy_id")
    private SalaryPolicyEntity salaryPolicy;

    private ShiftTypeEnum shiftTypeEnum;

    @ManyToMany
    @JoinTable(name = "job_shifts",
            joinColumns = @JoinColumn(name = "job_role_id"),
            inverseJoinColumns = @JoinColumn(name = "shift_policy_id"))
    private List<ShiftPolicyEntity> shiftPolicies;
}
