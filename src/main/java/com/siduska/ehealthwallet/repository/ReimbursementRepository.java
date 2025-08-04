package com.siduska.ehealthwallet.repository;

import com.siduska.ehealthwallet.entitiy.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {
}
