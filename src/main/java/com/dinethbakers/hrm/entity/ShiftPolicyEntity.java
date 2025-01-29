package com.dinethbakers.hrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shift_policy")
@Data
public class ShiftPolicyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalTime totalHours;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @JsonIgnore
    @ManyToMany(mappedBy = "shiftPolicies")
    private List<JobRoleEntity> jobRoles;
}
