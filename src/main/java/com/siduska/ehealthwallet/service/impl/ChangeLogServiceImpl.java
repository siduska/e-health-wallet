package com.siduska.ehealthwallet.service.impl;


import com.siduska.ehealthwallet.entitiy.ChangeLog;
import com.siduska.ehealthwallet.entitiy.StatusEnum;
import com.siduska.ehealthwallet.service.ChangeLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ChangeLogServiceImpl implements ChangeLogService {


    @Override
    public ChangeLog createChangeLog(String oldStatus, String newStatus, String description, String userName) {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setDescription(description);
        changeLog.setChangeDateTime(LocalDateTime.now());
        changeLog.setOldStatus(StatusEnum.valueOf(oldStatus));
        changeLog.setNewStatus(StatusEnum.valueOf(newStatus));
        changeLog.setUserName(userName);

        return changeLog;
    }
}
