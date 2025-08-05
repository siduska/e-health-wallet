package com.siduska.ehealthwallet.service;

import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.entitiy.Reimbursement;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

public interface ReimbursementService {

    Reimbursement updateReimbursement(Long id, UpdateReimbursementRequest request);
    Iterable<ReimbursementDto> getAllReimbursements();
    List<Reimbursement> getAllReimbursementsByStatus(String status);
    Reimbursement getReimbursementById(Long id);

}
