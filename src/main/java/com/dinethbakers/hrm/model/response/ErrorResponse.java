package com.dinethbakers.hrm.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private static final String status = "Failed";
    private String message;
}
