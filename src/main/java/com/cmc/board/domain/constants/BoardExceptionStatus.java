package com.cmc.board.domain.constants;

import com.cmc.global.common.interfaces.StatusCode;

public enum BoardExceptionStatus implements StatusCode {
    // 카테고리
    CATEGORY_NAME_EMPTY,
    CATEGORY_NAME_TOO_LONG,
}
