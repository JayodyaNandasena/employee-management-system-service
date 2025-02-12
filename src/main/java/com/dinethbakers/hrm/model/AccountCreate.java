package com.dinethbakers.hrm.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountCreate {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[\\d\\W])[A-Za-z\\d\\W]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, and at least one digit or special character"
    )
    private String password;

    private Boolean isManager;
}
