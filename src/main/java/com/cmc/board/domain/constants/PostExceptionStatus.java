package com.cmc.board.domain.constants;

import com.cmc.global.common.interfaces.StatusCode;

public enum PostExceptionStatus implements StatusCode {
    POST_TITLE_BLANK,
    POST_TITLE_TOO_LONG,
    POST_CONTENT_TOO_SHORT,
    POST_CONTENT_BLANK,
    MISMATCH_POST_AUTHOR,
}
