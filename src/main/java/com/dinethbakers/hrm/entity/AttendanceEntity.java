package com.dinethbakers.hrm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
@Data
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer attendanceId;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime timeIn;
    private LocalTime timeOut;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private EmployeeEntity employee;
}
