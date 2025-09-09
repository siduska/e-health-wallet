package com.siduska.ehealthwallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReimbursementWithLogDto {

        private Long id;
        private String patientName;
        private String identificationNumber;
        private String medicalProcedure;
        private BigDecimal cost;
        private String status;
        private String description;

}
