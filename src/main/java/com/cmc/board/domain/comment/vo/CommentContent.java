package com.cmc.board.domain.comment.vo;

import static com.cmc.board.domain.constants.CommentExceptionStatus.COMMENT_CONTENT_BLANK;
import static com.cmc.board.domain.constants.CommentExceptionStatus.COMMENT_CONTENT_TOO_LONG;

import com.cmc.global.common.exception.client.BadRequestException;
import com.cmc.global.common.utils.StringUtils;

public record CommentContent(String value) {

    private static final int MAX_COMMENT_LENGTH = 255;

    public CommentContent {
        if (StringUtils.isBlank(value)) {
            throw new BadRequestException(COMMENT_CONTENT_BLANK);
        }
        if (StringUtils.isOverLimit(value, MAX_COMMENT_LENGTH)) {
            throw new BadRequestException(COMMENT_CONTENT_TOO_LONG);
        }
    }
}
