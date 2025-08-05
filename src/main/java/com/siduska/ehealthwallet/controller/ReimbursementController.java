package com.siduska.ehealthwallet.controller;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.entitiy.Reimbursement;
import com.siduska.ehealthwallet.mapper.ReimbursementMapper;
import com.siduska.ehealthwallet.repository.ReimbursementRepository;
import com.siduska.ehealthwallet.service.ReimbursementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reimbursements")
public class ReimbursementController {

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementMapper reimbursementMapper;
    private final ReimbursementService reimbursementService;

    @GetMapping
    public Iterable<ReimbursementDto> getAllReimbursements() {
        return reimbursementService.getAllReimbursements();
    }

    @GetMapping("/filter")
    public List<ReimbursementDto> getAllReimbursementsByStatus(@RequestParam("filter") String statusFilter) {
        List<Reimbursement> rem = reimbursementService.getAllReimbursementsByStatus(statusFilter);
        return  rem.stream().map(reimbursementMapper::toReimbursementDto).toList();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReimbursementDto> getReimbursementById(@PathVariable(name = "id") Long id) {
        var reimbursement = reimbursementService.getReimbursementById(id);

        if(reimbursement == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reimbursementMapper.toReimbursementDto(reimbursement));
    }

    @PostMapping
    public ResponseEntity<?> createReimbursement(
            @RequestBody CreateReimbursementRequest request,
            UriComponentsBuilder uriBuilder
    ) {

        var reimbursement = reimbursementMapper.toEntity(request);
        reimbursementRepository.save(reimbursement);

        var reimbursementDto = reimbursementMapper.toReimbursementDto(reimbursement);
        var uri = uriBuilder.path("/reimbursements/{id}").buildAndExpand(reimbursementDto.getId()).toUri();

        return ResponseEntity.created(uri).body(reimbursementDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReimbursementDto> updateReimbursement(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateReimbursementRequest request
    ){
        var reimbursement = reimbursementService.updateReimbursement(id, request);

        if (reimbursement == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reimbursementMapper.toReimbursementDto(reimbursement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReimbursement(@PathVariable(name = "id") Long id) {
        var reimbursement = reimbursementService.getReimbursementById(id);

        if (reimbursement == null) {
            return ResponseEntity.notFound().build();
        }

        reimbursementRepository.delete(reimbursement);

        return ResponseEntity.noContent().build();
    }

}

