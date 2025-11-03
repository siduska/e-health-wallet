package com.siduska.ehealthwallet.mesaging.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReimbursementStatusChanged {

    private Long reimbursementId;
    private String identificationNumber;
    private String oldStatus;
    private String newStatus;
    private String changedBy;
    private LocalDateTime changedAt;

}
