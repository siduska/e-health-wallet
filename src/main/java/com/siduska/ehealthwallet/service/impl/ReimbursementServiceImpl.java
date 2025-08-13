package com.siduska.ehealthwallet.service.impl;

import com.siduska.ehealthwallet.conf.ReimbursementNotFoundException;
import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.entitiy.Reimbursement;
import com.siduska.ehealthwallet.entitiy.StatusEnum;
import com.siduska.ehealthwallet.mapper.ReimbursementMapper;
import com.siduska.ehealthwallet.repository.ReimbursementRepository;
import com.siduska.ehealthwallet.service.ChangeLogService;
import com.siduska.ehealthwallet.service.ReimbursementService;
import com.siduska.ehealthwallet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@AllArgsConstructor
public class ReimbursementServiceImpl implements ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementMapper reimbursementMapper;
    private final ChangeLogService changelogService;
    private final UserService userService;

    public ReimbursementDto updateReimbursement(Long id, UpdateReimbursementRequest request) {

        var reimbursement = getExistingReimbursementById(id);
        checkReimbursementStatus(request.getStatus().toUpperCase());

        String oldStatus = reimbursement.getStatus().toString();
        reimbursementMapper.updateReimbursement(request, reimbursement);
        reimbursementRepository.save(reimbursement);

        String newStatus = reimbursement.getStatus().toString().toUpperCase();
        changelogService.createChangeLog(oldStatus, newStatus, request.getDescription(),userService.getUserName());

        return reimbursementMapper.toReimbursementDto(reimbursement);
    }

    @Override
    public Iterable<ReimbursementDto> getAllReimbursements() {
        return reimbursementRepository.findAll()
                .stream().map(reimbursementMapper::toReimbursementDto).toList();
    }

    @Override
    public List<ReimbursementDto> getAllReimbursementsByStatus(String status) {
        List<Reimbursement> rem = getAllByStatus(status.toUpperCase());
        return  rem.stream().map(reimbursementMapper::toReimbursementDto).toList();
    }

    @Override
    public ReimbursementDto getReimbursementById(Long id) {
        Reimbursement reimbursement = reimbursementRepository.findById(id)
                .orElseThrow(() -> new ReimbursementNotFoundException(id));
        return reimbursementMapper.toReimbursementDto(reimbursement);
    }

    @Override
    public ReimbursementDto createReimbursement(CreateReimbursementRequest request) {
        checkReimbursementStatus(request.getStatus().toUpperCase());
        var reimbursement = reimbursementMapper.toEntity(request);
        reimbursementRepository.save(reimbursement);
        return reimbursementMapper.toReimbursementDto(reimbursement);
    }

    @Override
    public void deleteById(Long id) {
        reimbursementRepository.findById(id)
                .orElseThrow(() -> new ReimbursementNotFoundException(id));
        reimbursementRepository.deleteById(id);
    }

    public List<Reimbursement> getAllByStatus(String status) {
        return reimbursementRepository.findAll()
                .stream()
                .filter(c -> c.getStatus().equals(StatusEnum.valueOf(status)))
                .toList();
    }

    public Reimbursement getExistingReimbursementById(Long id) {
        return reimbursementRepository.findById(id)
                .orElseThrow(() -> new ReimbursementNotFoundException(id));
    }

    public void checkReimbursementStatus(String status){
        if (!StatusEnum.exists(status)) {
            throw new IllegalArgumentException("Invalid status: " + status +
                    ". Allowed statuses: " + Arrays.toString(StatusEnum.values()));
        }
    }
}
