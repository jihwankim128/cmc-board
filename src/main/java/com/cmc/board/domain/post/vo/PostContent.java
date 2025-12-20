package com.cmc.board.domain.post.vo;

import static com.cmc.board.domain.constants.PostExceptionStatus.POST_CONTENT_BLANK;
import static com.cmc.board.domain.constants.PostExceptionStatus.POST_CONTENT_TOO_SHORT;

import com.cmc.global.common.exception.client.BadRequestException;
import com.cmc.global.common.utils.StringUtils;

public record PostContent(String value) {

    private static final int MIN_POST_LENGTH = 5;

    public PostContent {
        if (StringUtils.isBlank(value)) {
            throw new BadRequestException(POST_CONTENT_BLANK);
        }
        if (StringUtils.isUnderMin(value, MIN_POST_LENGTH)) {
            throw new BadRequestException(POST_CONTENT_TOO_SHORT, MIN_POST_LENGTH);
        }
    }
}
