package com.siduska.ehealthwallet.controller;

import com.siduska.ehealthwallet.dto.CreateReimbursementRequest;
import com.siduska.ehealthwallet.dto.ReimbursementDto;
import com.siduska.ehealthwallet.dto.UpdateReimbursementRequest;
import com.siduska.ehealthwallet.service.ReimbursementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reimbursements")
@Tag(name = "Reimbursements", description = "Reimbursements API")
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    @GetMapping
    @Operation(summary = "Get all reimbursements")
    public Iterable<ReimbursementDto> getAllReimbursements() {
        return reimbursementService.getAllReimbursements();
    }

    @GetMapping("/filter")
    @Operation(summary = "Get all reimbursements by status")
    public List<ReimbursementDto> getAllReimbursementsByStatus(
            @Parameter(description = "Reimbursement status")
            @RequestParam("filter") String statusFilter) {
        return reimbursementService.getAllReimbursementsByStatus(statusFilter);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get reimbursement by id")
    public ResponseEntity<ReimbursementDto> getReimbursementById(
            @Parameter(description = "Reimbursement id")
            @Valid @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(reimbursementService.getReimbursementById(id));
    }

    @PostMapping
    @Operation(summary = "Create new reimbursement")
    public ResponseEntity<?> createReimbursement(
            @Valid @RequestBody CreateReimbursementRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        var reimbursementDto = reimbursementService.createReimbursement(request);
        var uri = uriBuilder.path("/reimbursements/{id}").buildAndExpand(reimbursementDto.getId()).toUri();
        return ResponseEntity.created(uri).body(reimbursementDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update reimbursement")
    public ResponseEntity<?> updateReimbursement(
            @Parameter(description = "Reimbursement id")
            @Valid @PathVariable(name = "id") Long id,
            @Valid @RequestBody UpdateReimbursementRequest request
    ){
        return ResponseEntity.ok(reimbursementService.updateReimbursement(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete reimbursement")
    public ResponseEntity<Void> deleteReimbursement(@Parameter(description = "Reimbursement id")
                                                    @Valid @PathVariable(name = "id") Long id) {
        reimbursementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

