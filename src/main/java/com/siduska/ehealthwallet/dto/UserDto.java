package com.siduska.ehealthwallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
}
