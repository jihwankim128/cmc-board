package com.cmc.board.domain.exception;

import com.cmc.board.domain.constants.CategoryExceptionStatus;
import com.cmc.global.common.exception.client.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException() {
        super(CategoryExceptionStatus.CATEGORY_NOT_FOUND);
    }
}
