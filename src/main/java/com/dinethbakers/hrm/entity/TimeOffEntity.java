package com.dinethbakers.hrm.entity;

import com.dinethbakers.hrm.util.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

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
    private StatusEnum status;

    private LocalDateTime approvedDateTime;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "approved_by", referencedColumnName = "employee_id")
    private EmployeeEntity manager;


}
