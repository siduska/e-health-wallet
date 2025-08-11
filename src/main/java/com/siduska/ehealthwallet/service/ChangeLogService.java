package com.siduska.ehealthwallet.service;

public interface ChangeLogService {

    void createChangeLog(String oldStatus, String newStatus, String description, String serName);
}
