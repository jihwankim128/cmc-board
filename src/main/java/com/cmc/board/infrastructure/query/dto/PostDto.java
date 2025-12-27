package com.cmc.board.infrastructure.query.dto;

import com.cmc.board.infrastructure.database.jpa.entity.PostEntity;
import com.cmc.user.application.query.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    @Schema(description = "게시글 식별자", example = "1L")
    Long id;
    @Schema(description = "게시글 작성자 정보")
    UserDto author;
    @Schema(description = "게시글 카테고리 정보")
    CategoryDto category;
    @Schema(description = "게시글 제목", example = "새로운 게시글")
    String title;
    @Schema(description = "게시글 내용", example = "새로운 내용")
    String content;
    @Schema(description = "게시글 작성일", example = "작성일자")
    Instant createdAt;
    @Schema(description = "게시글 작성자 여부")
    Boolean authorMe;
    @Schema(description = "게시글 수정 여부")
    Boolean modified;

    public static PostDto of(PostEntity post, UserDto author, CategoryDto category, Long userId) {
        return new PostDto(
                post.getId(),
                author,
                category,
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getAuthorId().equals(userId),
                post.isModified()
        );
    }
}
