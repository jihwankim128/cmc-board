package com.cmc.board.domain.constants;

import com.cmc.global.common.interfaces.StatusCode;

public enum CommentExceptionStatus implements StatusCode {
    COMMENT_CONTENT_BLANK,
    COMMENT_CONTENT_TOO_LONG,
    CANNOT_REPLY_TO_DELETED_COMMENT,
    COMMENT_DEPTH_EXCEED,
    NOT_FOUND_COMMENT,
    MISMATCH_COMMENT_AUTHOR
}
