package com.cmc.board.domain.constants;

import com.cmc.global.common.interfaces.StatusCode;

public enum BookmarkExceptionStatus implements StatusCode {
    BOOKMARK_REQUIRED_POST_ID,
    BOOKMARK_DUPLICATED
}
