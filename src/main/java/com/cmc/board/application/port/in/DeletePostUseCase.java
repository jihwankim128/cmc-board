package com.cmc.board.application.port.in;

public interface DeletePostUseCase {

    void delete(Long userId, Long postId);
}
