package com.siduska.ehealthwallet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Data
public class UserDto {

    private Long id;
    private String name;

    @NotBlank
    private String email;
}
