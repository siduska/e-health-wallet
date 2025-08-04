package com.siduska.ehealthwallet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateReimbursementRequest {

    private String patientName;
    private String identificationNumber;
    private String medicalProcedure;
    private BigDecimal cost;
    private String status;
}
