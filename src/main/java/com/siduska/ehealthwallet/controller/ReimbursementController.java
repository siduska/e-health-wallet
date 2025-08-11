package com.siduska.ehealthwallet.controller;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
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

    private final ReimbursementService reimbursementService;

    @GetMapping
    public Iterable<ReimbursementDto> getAllReimbursements() {
        return reimbursementService.getAllReimbursements();
    }

    @GetMapping("/filter")
    public List<ReimbursementDto> getAllReimbursementsByStatus(@RequestParam("filter") String statusFilter) {

        return reimbursementService.getAllReimbursementsByStatus(statusFilter);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReimbursementDto> getReimbursementById(@PathVariable(name = "id") Long id) {
        var reimbursement = reimbursementService.getReimbursementById(id);

        if(reimbursement == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reimbursement);
    }

    @PostMapping
    public ResponseEntity<?> createReimbursement(
            @RequestBody CreateReimbursementRequest request,
            UriComponentsBuilder uriBuilder
    ) {

        var reimbursementDto = reimbursementService.createReimbursement(request);
        var uri = uriBuilder.path("/reimbursements/{id}").buildAndExpand(reimbursementDto.getId()).toUri();

        return ResponseEntity.created(uri).body(reimbursementDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReimbursementDto> updateReimbursement(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateReimbursementRequest request
    ){
        var reimbursement = reimbursementService.getReimbursementById(id);

        if (reimbursement == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reimbursementService.updateReimbursement(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReimbursement(@PathVariable(name = "id") Long id) {
        var reimbursement = reimbursementService.getReimbursementById(id);

        if (reimbursement == null) {
            return ResponseEntity.notFound().build();
        }

        reimbursementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

