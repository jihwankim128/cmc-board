package com.cmc.board.domain.post.vo;

import static com.cmc.board.domain.constants.PostExceptionStatus.POST_TITLE_BLANK;
import static com.cmc.board.domain.constants.PostExceptionStatus.POST_TITLE_TOO_LONG;

import com.cmc.global.common.exception.client.BadRequestException;
import com.cmc.global.common.utils.StringUtils;

public record PostTitle(String value) {

    private static final int MAX_LENGTH = 50;

    public PostTitle {
        if (StringUtils.isBlank(value)) {
            throw new BadRequestException(POST_TITLE_BLANK);
        }
        if (StringUtils.isOverLimit(value, MAX_LENGTH)) {
            throw new BadRequestException(POST_TITLE_TOO_LONG, MAX_LENGTH);
        }
    }
}
