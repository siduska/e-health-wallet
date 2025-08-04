package com.siduska.ehealthwallet.controller;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.mapper.ReimbursementMapper;
import com.siduska.ehealthwallet.repository.ReimbursementRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/reimbursements")
public class ReimbursementController {

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementMapper reimbursementMapper;

    @GetMapping
    public Iterable<ReimbursementDto> getAllReimbursements() {
        return  reimbursementRepository.findAll()
                .stream().map( reimbursementMapper::toReimbursementDto).toList();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReimbursementDto> getUserById(@PathVariable(name = "id") Long id) {
        var reimbursement = reimbursementRepository.findById(id).orElse(null);

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
        var reimbursement = reimbursementRepository.findById(id).orElse(null);
        if(reimbursement == null) {
            return ResponseEntity.notFound().build();
        }

        reimbursementMapper.updateReimbursement(request, reimbursement);
        reimbursementRepository.save(reimbursement);

        return ResponseEntity.ok(reimbursementMapper.toReimbursementDto(reimbursement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReimbursement(@PathVariable(name = "id") Long id) {
        var reimbursement = reimbursementRepository.findById(id).orElse(null);

        if(reimbursement == null) {
            return ResponseEntity.notFound().build();
        }

        reimbursementRepository.delete(reimbursement);
        return ResponseEntity.noContent().build();
    }

}

