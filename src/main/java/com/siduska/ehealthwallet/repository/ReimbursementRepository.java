package com.siduska.ehealthwallet.repository;

import com.siduska.ehealthwallet.entitiy.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {

    Optional<Reimbursement> findByIdentificationNumber(String identificationNumber);
}
