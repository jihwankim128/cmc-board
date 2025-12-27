package com.cmc.board.presentation.query.dto;

import com.cmc.board.infrastructure.database.jpa.entity.PostEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public record PostDto(
        @Schema(description = "게시글 식별자", example = "1L") Long id,
        @Schema(description = "게시글 작성자 식별자", example = "1L") Long authorId,
        @Schema(description = "게시글 카테고리 식별자", example = "1L") CategoryDto category,
        @Schema(description = "게시글 제목", example = "새로운 게시글") String title,
        @Schema(description = "게시글 내용", example = "새로운 내용") String content,
        @Schema(description = "게시글 작성일", example = "작성일자") Instant createdAt,
        @Schema(description = "게시글 작성자 여부") Boolean authorMe,
        @Schema(description = "게시글 수정 여부") Boolean modified
) {

    public static PostDto of(PostEntity post, CategoryDto category, Long userId) {
        return new PostDto(
                post.getId(),
                post.getAuthorId(),
                category,
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getAuthorId().equals(userId),
                post.isModified()
        );
    }
}
