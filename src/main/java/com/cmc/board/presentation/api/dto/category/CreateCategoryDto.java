package com.cmc.board.presentation.api.dto.category;

import jakarta.validation.constraints.NotEmpty;

public record CreateCategoryDto(
        @NotEmpty(message = "{category.name.empty}")
        String name
) {
}
