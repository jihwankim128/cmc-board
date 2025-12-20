package com.cmc.board.presentation.query.dto;

import com.cmc.board.domain.post.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record PostDto(
        @Schema(description = "게시글 식별자", example = "1L") Long id,
        @Schema(description = "게시글 작성자 식별자", example = "1L") Long authorId,
        @Schema(description = "게시글 카테고리 식별자", example = "1L") Long categoryId,
        @Schema(description = "게시글 제목", example = "새로운 게시글") String name,
        @Schema(description = "게시글 내용", example = "새로운 내용") String title,
        @Schema(description = "게시글 상태", example = "게시글 상태") PostStatus status
) {
}
