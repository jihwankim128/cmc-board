package com.cmc.board.application.port.in;

public interface DeleteCommentUseCase {
    void delete(Long userId, Long commentId);
}
