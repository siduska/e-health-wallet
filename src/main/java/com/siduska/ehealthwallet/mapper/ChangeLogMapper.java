package com.siduska.ehealthwallet.mapper;


import com.siduska.ehealthwallet.entitiy.ChangeLog;
import com.siduska.ehealthwallet.mesaging.event.ReimbursementStatusChanged;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChangeLogMapper {

    @Mapping(source = "reimbursement.identificationNumber", target = "identificationNumber")
    ReimbursementStatusChanged toEvent(ChangeLog log);
}
