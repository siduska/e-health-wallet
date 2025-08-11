package com.siduska.ehealthwallet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReimbursementDto {

    private Long id;
    private String patientName;
    private String identificationNumber;
    private String medicalProcedure;
    private BigDecimal cost = BigDecimal.ZERO;
    private String status;
}
