package com.siduska.ehealthwallet.repository;

import com.siduska.ehealthwallet.entitiy.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {
}
