package com.siduska.ehealthwallet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank
    String name;
    @NotBlank
    String email;
}
