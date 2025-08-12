package com.siduska.ehealthwallet.service;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;


import java.util.List;

public interface ReimbursementService {

    ReimbursementDto updateReimbursement(Long id, UpdateReimbursementRequest request);
    Iterable<ReimbursementDto> getAllReimbursements();
    List<ReimbursementDto> getAllReimbursementsByStatus(String status);
    ReimbursementDto getReimbursementById(Long id);
    ReimbursementDto createReimbursement(CreateReimbursementRequest request);
    void deleteById(Long id);
    boolean isStatusExist(String status);
}
