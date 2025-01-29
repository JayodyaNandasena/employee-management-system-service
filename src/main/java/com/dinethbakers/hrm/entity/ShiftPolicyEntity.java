package com.dinethbakers.hrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "shift_policy")
@Data
public class ShiftPolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "policy_id")
    private Integer policyId;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalTime totalHours;

    @JsonIgnore
    @ManyToMany(mappedBy = "shiftPolicies")
    private List<JobRoleEntity> jobRoles;
}
