package com.dinethbakers.hrm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "branch_mobile")
@Data
public class BranchMobileEntity implements Serializable {
    @Id
    private String number;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branch;
}
