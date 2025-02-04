package com.dinethbakers.hrm.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
    private static final String status = "Success";
    private Object data;
}
