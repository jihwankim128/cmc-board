package com.cmc.board.infrastructure.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public record BookmarkDto(
        @Schema(description = "북마크 식별자", example = "1") Long id,
        @Schema(description = "게시글 정보", example = "1") PostDto post,
        @Schema(description = "생성 시각") Instant createdAt
) {
}
