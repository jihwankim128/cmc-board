package com.cmc.board.application.port.in.command;

public record CreatePostCommand(Long authorId, Long categoryId, String title, String content) {
}
