package com.cmc.board.presentation.api.dto.comment;

import com.cmc.board.application.port.in.command.ReplyCommentCommand;
import jakarta.validation.constraints.NotEmpty;

public record ReplyCommentDto(
        @NotEmpty(message = "{comment.content.blank}") String content
) {
    public ReplyCommentCommand toCommand(Long authorId, Long parentCommentId) {
        return new ReplyCommentCommand(authorId, parentCommentId, content);
    }
}
