package com.cmc.board.domain.comment.vo;

import com.cmc.board.domain.constants.CommentExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;

public record CommentDepth(int value) {

    public static final CommentDepth BASE_DEPTH = new CommentDepth(0);

    private static final int MAX_COMMENT_DEPTH = 1;

    public CommentDepth {
        if (value < 0) {
            throw new IllegalArgumentException("댓글은 0 Depth부터 시작합니다.");
        }
        if (value > MAX_COMMENT_DEPTH) {
            throw new BadRequestException(CommentExceptionStatus.COMMENT_DEPTH_EXCEED, MAX_COMMENT_DEPTH);
        }
    }

    public CommentDepth child() {
        return new CommentDepth(value + 1);
    }
}
