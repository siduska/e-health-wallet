package com.siduska.ehealthwallet.service;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.ReimbursementWithLogDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ReimbursementService {

    ReimbursementDto updateReimbursement(Long id, UpdateReimbursementRequest request);
    Iterable<ReimbursementDto> getAllReimbursements();
    Page<ReimbursementWithLogDto> getAllWithLog(Pageable pageable);
    List<ReimbursementDto> getAllReimbursementsByStatus(String status);
    ReimbursementDto getReimbursementById(Long id);
    ReimbursementDto createReimbursement(CreateReimbursementRequest request);
    void deleteById(Long id);
}
