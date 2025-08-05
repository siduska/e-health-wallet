package com.siduska.ehealthwallet.service;

import com.siduska.ehealthwallet.entitiy.ChangeLog;

public interface ChangeLogService {

    ChangeLog createChangeLog(String oldStatus, String newStatus, String description, String serName);
}
