package com.siduska.ehealthwallet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateReimbursementRequest {

    @NotBlank(message = "Status field is necessary")
    String status;
    String description;
}
