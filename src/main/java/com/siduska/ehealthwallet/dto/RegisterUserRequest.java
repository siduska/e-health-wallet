package com.siduska.ehealthwallet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {

    @NotBlank(message = "Email is required")
    @Size(max = 255, message = "Maximal size of email is 255 characters")
    String name;

    @NotBlank(message = "Email is required")
    @Email
    String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "Password should have minimum of 8 characters and maximum of 20 characters")
    String password;
}
