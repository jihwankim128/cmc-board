package com.cmc.board.domain.exception;

import com.cmc.board.domain.constants.CategoryExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;

public class DuplicatedCategoryException extends BadRequestException {
    public DuplicatedCategoryException() {
        super(CategoryExceptionStatus.CATEGORY_NAME_DUPLICATED);
    }
}
