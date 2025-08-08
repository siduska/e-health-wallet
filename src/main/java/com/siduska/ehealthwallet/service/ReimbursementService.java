package com.siduska.ehealthwallet.service;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.entitiy.Reimbursement;


import java.util.List;

public interface ReimbursementService {

    Reimbursement updateReimbursement(Long id, UpdateReimbursementRequest request);
    Iterable<ReimbursementDto> getAllReimbursements();
    List<Reimbursement> getAllReimbursementsByStatus(String status);
    Reimbursement getReimbursementById(Long id);
    Reimbursement createReimbursement(CreateReimbursementRequest request);
    void delete(Reimbursement reimbursement);
}
