package com.dinethbakers.hrm.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Branch {

    @NotBlank(message = "Branch ID cannot be empty")
    private String branchId;

    @NotBlank(message = "Branch name cannot be empty")
    private String name;

    @NotNull(message = "Latitude cannot be null")
    @Digits(integer = 3, fraction = 6, message = "Latitude must be a valid coordinate with up to 6 decimal places")
    private BigDecimal latitude;

    @NotNull(message = "Longitude cannot be null")
    @Digits(integer = 3, fraction = 6, message = "Longitude must be a valid coordinate with up to 6 decimal places")
    private BigDecimal longitude;

    @NotBlank(message = "Address cannot be empty")
    private String address;
}
