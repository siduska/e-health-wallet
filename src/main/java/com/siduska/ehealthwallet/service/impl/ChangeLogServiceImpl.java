package com.siduska.ehealthwallet.service.impl;


import com.siduska.ehealthwallet.entitiy.ChangeLog;
import com.siduska.ehealthwallet.entitiy.Reimbursement;
import com.siduska.ehealthwallet.entitiy.StatusEnum;
import com.siduska.ehealthwallet.repository.ChangeLogRepository;
import com.siduska.ehealthwallet.service.ChangeLogService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChangeLogServiceImpl implements ChangeLogService {

    private final ChangeLogRepository changeLogRepository;
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void createChangeLog(String oldStatus, String newStatus, String description, String userName, Reimbursement reimbursement) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setReimbursement(reimbursement);
        changeLog.setDescription(description);
        changeLog.setOldStatus(StatusEnum.valueOf(oldStatus));
        changeLog.setNewStatus(StatusEnum.valueOf(newStatus));
        changeLog.setUserName(userName);
        changeLogRepository.save(changeLog);
        LOGGER.info("Updating reimbursement{}", changeLog.toString());
    }
}
