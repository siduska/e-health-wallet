package com.siduska.ehealthwallet.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String patientName;
    private String identificationNumber;
    private String medicalProcedure;
    private BigDecimal cost;

    //@Enumerated(EnumType.STRING)
    @Column(length = 8)
    private StatusEnum status;

}
