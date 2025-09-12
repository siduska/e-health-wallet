package com.siduska.ehealthwallet.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@ToString
public class LoginRequest {
    private String username;
    private String password;
}
