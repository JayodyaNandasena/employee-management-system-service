package com.dinethbakers.hrm.model;

import com.dinethbakers.hrm.util.GenderEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeCreate {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String nic;
    private LocalDate dob;
    private String profilePicture;
    private LocalDate hiredDate;
    private String address;
    private String email;
    private GenderEnum genderEnum;
    private String branchName;
    private String jobRoleTitle;
    private AccountCreate account;
}
