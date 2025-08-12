package com.siduska.ehealthwallet.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateReimbursementRequest {

    String status;
    String description;
}
