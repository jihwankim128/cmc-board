package com.cmc.user.domain.vo;

import static com.cmc.user.domain.constants.UserExceptionStatus.USER_NICKNAME_EMPTY;
import static com.cmc.user.domain.constants.UserExceptionStatus.USER_NICKNAME_OUT_OF_RANGE;

import com.cmc.global.common.exception.client.BadRequestException;
import com.cmc.global.common.utils.StringUtils;

public record Nickname(String value) {

    private static final int MIN_NICKNAME_LENGTH = 2;
    private static final int MAX_NICKNAME_LENGTH = 16;

    public Nickname {
        if (StringUtils.isBlank(value)) {
            throw new BadRequestException(USER_NICKNAME_EMPTY);
        }
        if (StringUtils.isOutOfRange(value, MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH)) {
            throw new BadRequestException(USER_NICKNAME_OUT_OF_RANGE, MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH);
        }
    }
}
