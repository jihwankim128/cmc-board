package com.cmc.board.application.port.in;

public interface CreateBookmarkUseCase {
    void create(Long postId, Long userId);
}
