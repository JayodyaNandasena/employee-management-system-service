package com.dinethbakers.hrm.entity;

import com.dinethbakers.hrm.util.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "over_time")
@Data
public class OverTimeEntity {
    @Id
    @Column(name = "request_id")
    private String requestId;

    private LocalDate date;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    private String text;

    private Double paymentAmount;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private LocalDateTime approvedDateTime;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requester_id", referencedColumnName = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "approver_id", referencedColumnName = "employee_id")
    private EmployeeEntity manager;


}