package com.dinethbakers.hrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employee_mobile")
@Data
public class EmployeeMobileEntity {
    @Id
    private String number;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
}
