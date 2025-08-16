package com.siduska.ehealthwallet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank
    String username;
    @NotBlank
    String email;
}
