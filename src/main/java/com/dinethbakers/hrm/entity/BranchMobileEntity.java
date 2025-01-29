package com.dinethbakers.hrm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "branch_mobile")
@Data
public class BranchMobileEntity {
    @Id
    private String number;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branch;
}
