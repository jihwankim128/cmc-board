package com.cmc.board.presentation.api.dto.post;

import com.cmc.board.application.port.in.command.UpdatePostCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdatePostDto(
        @NotNull Long categoryId,
        @NotEmpty(message = "{post.title.blank}") String title,
        @NotEmpty(message = "{post.content.blank}") String content
) {
    public UpdatePostCommand toCommand(Long postId, Long authorId) {
        return new UpdatePostCommand(postId, authorId, categoryId, title, content);
    }
}
