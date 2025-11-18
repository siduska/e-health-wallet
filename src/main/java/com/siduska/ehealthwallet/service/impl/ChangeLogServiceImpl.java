package com.siduska.ehealthwallet.service.impl;


import com.siduska.ehealthwallet.entitiy.ChangeLog;
import com.siduska.ehealthwallet.entitiy.Reimbursement;
import com.siduska.ehealthwallet.entitiy.StatusEnum;
import com.siduska.ehealthwallet.mesaging.event.ReimbursementStatusChanged;
import com.siduska.ehealthwallet.repository.ChangeLogRepository;
import com.siduska.ehealthwallet.service.ChangeLogService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class ChangeLogServiceImpl implements ChangeLogService {

    private final ChangeLogRepository changeLogRepository;
    private static final Logger LOGGER = LogManager.getLogger();
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void createChangeLog(String oldStatus, String newStatus, String description, String username, Reimbursement reimbursement) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setReimbursement(reimbursement);
        changeLog.setDescription(description);
        changeLog.setOldStatus(StatusEnum.valueOf(oldStatus));
        changeLog.setNewStatus(StatusEnum.valueOf(newStatus));
        changeLog.setUsername(username);
        changeLogRepository.save(changeLog);

        ReimbursementStatusChanged event = new ReimbursementStatusChanged();
        event.setReimbursementId(reimbursement.getId());
        event.setIdentificationNumber(reimbursement.getIdentificationNumber());
        event.setOldStatus(oldStatus);
        event.setNewStatus(newStatus);
        event.setChangedBy(username);
        event.setChangedAt(OffsetDateTime.now());

        kafkaTemplate.send("reimbursement-status-changed", event);


        LOGGER.info("Updating reimbursement{}", changeLog.toString());
    }
}
