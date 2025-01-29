package com.dinethbakers.hrm.entity;

import com.dinethbakers.hrm.util.ShiftTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "job_role")
@Data
public class JobRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String title;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "jobRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeeEntity> employees;

    @ManyToOne
    @JoinColumn(name = "leave_policy_id", referencedColumnName = "id")
    private LeavePolicyEntity leavePolicy;

    @ManyToOne
    @JoinColumn(name = "salary_policy_id", referencedColumnName = "id")
    private SalaryPolicyEntity salaryPolicy;

    private ShiftTypeEnum shiftType;

    @ManyToMany
    @JoinTable(name = "job_shifts",
            joinColumns = @JoinColumn(name = "job_role_id"),
            inverseJoinColumns = @JoinColumn(name = "shift_policy_id"))
    private List<ShiftPolicyEntity> shiftPolicies;
}
