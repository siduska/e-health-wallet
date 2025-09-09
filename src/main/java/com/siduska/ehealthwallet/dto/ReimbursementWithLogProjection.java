package com.siduska.ehealthwallet.dto;

import java.math.BigDecimal;

public interface ReimbursementWithLogProjection {

    Long getId();
    String getPatientName();
    String getIdentificationNumber();
    String getMedicalProcedure();
    BigDecimal getCost();
    String getStatus();
    String getDescription();

}
