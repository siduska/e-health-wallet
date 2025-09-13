package com.siduska.ehealthwallet.controller;

import com.siduska.ehealthwallet.dto.*;
import com.siduska.ehealthwallet.service.ReimbursementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reimbursements")
@Tag(name = "Reimbursements", description = "Reimbursements API")
@SecurityRequirement(name = "ehealthwalletapi")
@CrossOrigin(origins = "https://e-health-wallet-production.up.railway.app/")
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

    @GetMapping("/all")
    @Operation(summary = "Get reimbursements page with 10 rows and logs")
    public ResponseEntity<PageResponse<ReimbursementWithLogDto>> getReimbursements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ReimbursementWithLogDto> reimbursements = reimbursementService.getAllWithLog(PageRequest.of(page, size));
        PageResponse<ReimbursementWithLogDto> response = PageResponse.from(reimbursements);
        HttpHeaders headers = response.toHttpHeaders();

        if (reimbursements.isEmpty()) {
            return ResponseEntity.noContent().headers(headers).build();
        }

        return ResponseEntity.ok().headers(headers).body(response);
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

