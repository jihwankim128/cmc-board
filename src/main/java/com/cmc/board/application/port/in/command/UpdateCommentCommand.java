package com.cmc.board.application.port.in.command;

public record UpdateCommentCommand(Long authorId, Long commentId, String content) {
}
