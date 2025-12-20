package com.cmc.board.presentation.api.dto.comment;

import com.cmc.board.application.port.in.command.WriteCommentCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record WriteCommentDto(
        @NotNull(message = "{comment.post.notNull}") Long postId,
        @NotEmpty(message = "{comment.content.blank}") String content
) {
    public WriteCommentCommand toCommand(Long authorId) {
        return new WriteCommentCommand(authorId, postId, content);
    }
}
