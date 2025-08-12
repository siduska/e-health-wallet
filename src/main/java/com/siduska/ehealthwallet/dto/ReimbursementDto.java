package com.siduska.ehealthwallet.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ReimbursementDto {

    private Long id;
    private String patientName;
    private String identificationNumber;
    private String medicalProcedure;
    private BigDecimal cost = BigDecimal.ZERO;
    private String status;
}
