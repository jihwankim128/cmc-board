package com.cmc.board.presentation.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateCategoryDto(
        @NotEmpty(message = "{category.name.empty}")
        @Size(max = 20, message = "{category.name.tooLong}")
        String name
) {
}
