package com.cmc.board.presentation.api.dto.post;

import com.cmc.board.application.port.in.command.CreatePostCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreatePostDto(
        @NotNull Long categoryId,
        @NotEmpty(message = "{post.title.empty}") String title,
        @NotEmpty(message = "{post.content.empty}") String content
) {
    public CreatePostCommand toCommand(Long authorId) {
        return new CreatePostCommand(authorId, categoryId, title, content);
    }
}
