package com.cmc.user.presentation.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupDto(
        @NotBlank String nickname,
        @Email @NotBlank String email,
        @NotBlank String password
) {
}
