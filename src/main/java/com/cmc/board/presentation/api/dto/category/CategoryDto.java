package com.cmc.board.presentation.api.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryDto(
        @Schema(description = "카테고리 식별자", example = "1L") Long id,
        @Schema(description = "카테고리 명", example = "새로운 카테고리") String name) {
}
