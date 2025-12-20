package com.cmc.board.presentation.api.dto.comment;

import com.cmc.board.application.port.in.command.UpdateCommentCommand;
import jakarta.validation.constraints.NotEmpty;

public record UpdateCommentDto(
        @NotEmpty(message = "{comment.content.blank}") String content
) {
    public UpdateCommentCommand toCommand(Long commentId, Long authorId) {
        return new UpdateCommentCommand(authorId, commentId, content);
    }
}
