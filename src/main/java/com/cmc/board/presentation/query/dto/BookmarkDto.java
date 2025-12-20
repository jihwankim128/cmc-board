package com.cmc.board.presentation.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public record BookmarkDto(
        @Schema(description = "북마크 식별자", example = "1") Long id,
        @Schema(description = "게시글 식별자", example = "1") Long postId,
        @Schema(description = "사용자 식별자", example = "1") Long userId,
        @Schema(description = "생성 시각") Instant createdAt
) {
}
