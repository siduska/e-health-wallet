package com.siduska.ehealthwallet.service.impl;

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
        var reimbursement = reimbursementRepository.findById(id).orElse(null);

        if (reimbursement == null) {
            return null;
        }
        String oldStatus = reimbursement.getStatus().toString();
        reimbursementMapper.updateReimbursement(request, reimbursement);
        reimbursementRepository.save(reimbursement);

        String newStatus = reimbursement.getStatus().toString();
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
        List<Reimbursement> rem = reimbursementRepository.findAll()
                .stream()
                .filter(c -> c.getStatus().equals(StatusEnum.valueOf(status)))
                .toList();
        return  rem.stream().map(reimbursementMapper::toReimbursementDto).toList();
    }

    @Override
    public ReimbursementDto getReimbursementById(Long id) {
        Reimbursement reimbursement = reimbursementRepository.findById(id).orElse(null);
        if (reimbursement != null) {
            return reimbursementMapper.toReimbursementDto(reimbursement);
        }
        return null;
    }

    @Override
    public ReimbursementDto createReimbursement(CreateReimbursementRequest request) {
        var reimbursement = reimbursementMapper.toEntity(request);
        reimbursementRepository.save(reimbursement);
        return reimbursementMapper.toReimbursementDto(reimbursement);
    }

    @Override
    public void deleteById(Long id) {
        reimbursementRepository.deleteById(id);
    }

    @Override
    public boolean isStatusExist(String status) {
        StatusEnum[] values = StatusEnum.values();
        var found = Arrays.stream(values).filter(s -> s.toString().equals(status)).findAny().orElse(null);
        return found == null;
    }

}
