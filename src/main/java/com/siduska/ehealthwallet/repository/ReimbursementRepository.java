package com.siduska.ehealthwallet.repository;

import com.siduska.ehealthwallet.dto.ReimbursementWithLogProjection;
import com.siduska.ehealthwallet.entitiy.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {

    Optional<Reimbursement> findByIdentificationNumber(String identificationNumber);

    @Query(
            value = """
            SELECT r.id AS id,
                   r.patient_name,
                   r.identification_number,
                   r.medical_procedure,
                   r.cost,
                   r.status AS status,
                   cl.description AS description
            FROM reimbursement r
            LEFT JOIN (
                SELECT *, ROW_NUMBER() OVER (PARTITION BY reimbursement_id ORDER BY change_date_time DESC) AS rn
                FROM change_log
            ) cl ON cl.reimbursement_id = r.id AND cl.rn = 1
            """,
            countQuery = "SELECT COUNT(*) FROM reimbursement",
            nativeQuery = true
    )
    Page<ReimbursementWithLogProjection> findAllWithLatestLogNative(Pageable pageable);
}
