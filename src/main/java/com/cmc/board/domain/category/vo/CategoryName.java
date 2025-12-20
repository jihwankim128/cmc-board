package com.cmc.board.domain.category.vo;

import static com.cmc.board.domain.constants.CategoryExceptionStatus.CATEGORY_NAME_EMPTY;
import static com.cmc.board.domain.constants.CategoryExceptionStatus.CATEGORY_NAME_TOO_LONG;

import com.cmc.global.common.exception.client.BadRequestException;
import com.cmc.global.common.utils.StringUtils;

public record CategoryName(String value) {

    private static final int MAX_LIMIT = 20;

    public CategoryName {
        if (StringUtils.isBlank(value)) {
            throw new BadRequestException(CATEGORY_NAME_EMPTY);
        }
        if (StringUtils.isOverLimit(value, MAX_LIMIT)) {
            throw new BadRequestException(CATEGORY_NAME_TOO_LONG, MAX_LIMIT);
        }
    }
}
