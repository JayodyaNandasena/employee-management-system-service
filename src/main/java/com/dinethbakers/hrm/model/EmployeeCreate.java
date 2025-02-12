package com.dinethbakers.hrm.model;

import com.dinethbakers.hrm.util.GenderEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeCreate {

    @NotBlank(message = "Employee ID cannot be empty")
    private String employeeId;

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "NIC cannot be empty")
    @Pattern(
            regexp = "^(\\d{9}[Vv]|\\d{12})$",
            message = "NIC must be 9 digits followed by 'V' or 12 digits"
    )
    private String nic;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    private String profilePicture;

    @NotNull(message = "Hired date cannot be null")
    @PastOrPresent(message = "Hired date must be in the past or today")
    private LocalDate hiredDate;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Gender must be specified")
    private GenderEnum genderEnum;

    @NotBlank(message = "Branch name cannot be empty")
    private String branchName;

    @NotBlank(message = "Job role title cannot be empty")
    private String jobRoleTitle;

    @NotNull(message = "Account details cannot be null")
    @Valid
    private AccountCreate account;
}
