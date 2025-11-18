package com.siduska.ehealthwallet.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private String identificationNumber;
    private String medicalProcedure;
    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private StatusEnum status;

    @OneToMany(mappedBy = "reimbursement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChangeLog> changeLogs = new ArrayList<>();

}
