package com.siduska.ehealthwallet.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateReimbursementRequest {

    private String patientName;

    @NotBlank(message = "Identification number is necessary")
    private String identificationNumber;

    private String medicalProcedure;

    @DecimalMin(value = "0.0")
    private BigDecimal cost;

    @NotBlank(message = "Status is necessary. Set either PENDING, APPROVED or REJECTED")
    private String status;
}
