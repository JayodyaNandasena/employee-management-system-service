package com.dinethbakers.hrm.entity;

import com.dinethbakers.hrm.util.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Table(name = "time_off")
@Data
public class TimeOffEntity {
    @Id
    private String requestId;
    private String text;
    @Column(nullable = false)
    private LocalDateTime requestDateTime;
    @Column(nullable = false)
    private LocalDateTime startDateTime;
    @Column(nullable = false)
    private LocalDateTime endDateTime;
    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;
    private LocalDateTime approvedDateTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "approved_by", referencedColumnName = "employee_id")
    private EmployeeEntity manager;


}
