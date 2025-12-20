package com.cmc.user.presentation.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @Email(message = "{user.email.invalid}") String email,
        @NotBlank(message = "{user.password.empty}") String password
) {
}
