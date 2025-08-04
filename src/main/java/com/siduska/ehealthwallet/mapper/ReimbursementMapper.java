package com.siduska.ehealthwallet.mapper;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.entitiy.Reimbursement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReimbursementMapper {

    ReimbursementDto toReimbursementDto(Reimbursement reimbursement);
    Reimbursement  toEntity(CreateReimbursementRequest request);
    void updateReimbursement(UpdateReimbursementRequest request, @MappingTarget Reimbursement reimbursement);
}
