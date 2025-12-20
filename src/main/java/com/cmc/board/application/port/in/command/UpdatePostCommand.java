package com.cmc.board.application.port.in.command;

public record UpdatePostCommand(Long postId, Long authorId, Long categoryId, String title, String content) {
}
