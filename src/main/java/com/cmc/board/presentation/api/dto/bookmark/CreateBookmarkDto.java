package com.cmc.board.presentation.api.dto.bookmark;

import jakarta.validation.constraints.NotNull;

public record CreateBookmarkDto(@NotNull Long postId) {
}
