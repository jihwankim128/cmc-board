package com.cmc.board.application.port.out;

public interface ValidatePostPort {
    boolean existsById(Long postId);
}
