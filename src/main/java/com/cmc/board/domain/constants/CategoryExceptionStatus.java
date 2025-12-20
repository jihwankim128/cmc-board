package com.cmc.board.domain.constants;

import com.cmc.global.common.interfaces.StatusCode;

public enum CategoryExceptionStatus implements StatusCode {
    CATEGORY_NAME_EMPTY,
    CATEGORY_NAME_TOO_LONG,
    CATEGORY_NAME_DUPLICATED,
    CATEGORY_NOT_FOUND,
}
