package com.cmc.board.presentation.query.dto;

import com.cmc.board.domain.comment.CommentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public record CommentDto(
        @Schema(description = "댓글 식별자", example = "1") Long id,
        @Schema(description = "게시글 식별자", example = "1") Long postId,
        @Schema(description = "부모 댓글 식별자", example = "null") Long parentId,
        @Schema(description = "작성자 식별자", example = "1") Long authorId,
        @Schema(description = "댓글 내용", example = "댓글입니다") String content,
        @Schema(description = "댓글 상태", example = "PUBLISHED") CommentStatus status,
        @Schema(description = "댓글 상태", example = "PUBLISHED") Instant createdAt
) {
}
