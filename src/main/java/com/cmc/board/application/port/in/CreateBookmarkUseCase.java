package com.cmc.board.application.port.in;

import jakarta.validation.constraints.NotNull;

public interface CreateBookmarkUseCase {
    void create(Long postId, Long userId);

    void delete(@NotNull Long aLong, Long userId);
}
