package com.siduska.ehealthwallet.service.impl;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.entitiy.ChangeLog;
import com.siduska.ehealthwallet.entitiy.Reimbursement;
import com.siduska.ehealthwallet.entitiy.StatusEnum;
import com.siduska.ehealthwallet.mapper.ReimbursementMapper;
import com.siduska.ehealthwallet.repository.ChangeLogRepository;
import com.siduska.ehealthwallet.repository.ReimbursementRepository;
import com.siduska.ehealthwallet.service.ChangeLogService;
import com.siduska.ehealthwallet.service.ReimbursementService;
import com.siduska.ehealthwallet.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ReimbursementServiceImpl implements ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementMapper reimbursementMapper;
    private final ChangeLogService changelogService;
    private final ChangeLogRepository changeLogRepository;
    private final UserService userService;
    private static final Logger LOGGER = LogManager.getLogger();

    public Reimbursement updateReimbursement(Long id, UpdateReimbursementRequest request) {
        var reimbursement = reimbursementRepository.findById(id).orElse(null);

        if (reimbursement == null) {
            return null;
        }
        String oldStatus = reimbursement.getStatus().toString();

        reimbursementMapper.updateReimbursement(request, reimbursement);
        reimbursementRepository.save(reimbursement);

        String newStatus = reimbursement.getStatus().toString();
        ChangeLog log = changelogService.createChangeLog(oldStatus, newStatus, request.getDescription(),userService.getUserName());
        changeLogRepository.save(log);
        LOGGER.info(log);

        return reimbursement;
    }

    @Override
    public Iterable<ReimbursementDto> getAllReimbursements() {
        return reimbursementRepository.findAll()
                .stream().map(reimbursementMapper::toReimbursementDto).toList();
    }

    @Override
    public List<Reimbursement> getAllReimbursementsByStatus(String status) {
        return reimbursementRepository.findAll()
                .stream()
                .filter(c -> c.getStatus().equals(StatusEnum.valueOf(status)))
                .toList();
    }

    @Override
    public Reimbursement getReimbursementById(Long id) {
        return reimbursementRepository.findById(id).orElse(null);
    }

    @Override
    public Reimbursement createReimbursement(CreateReimbursementRequest request) {
        var reimbursement = reimbursementMapper.toEntity(request);
        reimbursementRepository.save(reimbursement);
        return reimbursement;
    }

    @Override
    public void delete(Reimbursement reimbursement) {
        reimbursementRepository.delete(reimbursement);
    }

}
