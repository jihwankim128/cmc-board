package com.cmc.board.domain.constants;

import com.cmc.global.common.interfaces.StatusCode;

public enum CommentExceptionStatus implements StatusCode {
    COMMENT_AUTHOR_NOT_NULL,
    COMMENT_POST_NOT_NULL,
    COMMENT_CONTENT_BLANK,
    COMMENT_CONTENT_TOO_LONG,
    CANNOT_REPLY_TO_DELETED_COMMENT,
    COMMENT_DEPTH_EXCEED,
    COMMENT_NOT_FOUND,
    MISMATCH_COMMENT_AUTHOR
}
