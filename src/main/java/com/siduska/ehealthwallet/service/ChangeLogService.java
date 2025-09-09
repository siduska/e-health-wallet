package com.siduska.ehealthwallet.service;

import com.siduska.ehealthwallet.entitiy.Reimbursement;

public interface ChangeLogService {

    void createChangeLog(String oldStatus, String newStatus, String description, String serName, Reimbursement reimbursement);
}
