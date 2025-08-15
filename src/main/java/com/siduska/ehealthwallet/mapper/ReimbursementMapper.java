package com.siduska.ehealthwallet.mapper;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.entitiy.Reimbursement;
import com.siduska.ehealthwallet.entitiy.StatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ReimbursementMapper {

    @Mapping(source = "status", target = "status", qualifiedByName = "statusEnumToString")
    @Mapping(source = "id", target = "id")
    ReimbursementDto toReimbursementDto(Reimbursement reimbursement);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "status", target = "status", qualifiedByName = "stringToStatusEnum")
    Reimbursement  toEntity(CreateReimbursementRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patientName", ignore = true)
    @Mapping(target = "identificationNumber", ignore = true)
    @Mapping(target = "medicalProcedure", ignore = true)
    @Mapping(target = "cost", ignore = true)
    void updateReimbursement(UpdateReimbursementRequest request, @MappingTarget Reimbursement reimbursement);

    @Named("statusEnumToString")
    default String mapStatusEnumToString(StatusEnum status) {
        return status != null ? status.name() : null;
    }

    @Named("stringToStatusEnum")
    default StatusEnum mapStringToStatusEnum(String status) {
        return status != null ? StatusEnum.valueOf(status) : null;
    }
}
