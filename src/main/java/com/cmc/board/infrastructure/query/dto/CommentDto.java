package com.cmc.board.infrastructure.query.dto;

import com.cmc.board.infrastructure.database.jpa.entity.CommentEntity;
import com.cmc.user.application.query.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    @Schema(description = "댓글 식별자", example = "1")
    Long id;
    @Schema(description = "게시글 식별자", example = "1")
    Long postId;
    @Schema(description = "부모 댓글 식별자", example = "null")
    Long parentId;
    @Schema(description = "부모 댓글 식별자", example = "0")
    Integer depth;
    @Schema(description = "작성자 식별자", example = "1")
    UserDto author;
    @Schema(description = "댓글 내용", example = "댓글입니다")
    String content;
    @Schema(description = "댓글 상태", example = "PUBLISHED")
    Instant createdAt;
    @Schema(description = "댓글 작성자 여부")
    Boolean authorMe;
    @Schema(description = "댓글 수정 여부")
    Boolean modified;

    public static CommentDto of(CommentEntity comment, UserDto author, Long userId) {
        return new CommentDto(
                comment.getId(),
                comment.getPostId(),
                comment.getParentId(),
                comment.getDepth(),
                author,
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getAuthorId().equals(userId),
                comment.isModified()
        );
    }
}
