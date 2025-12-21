package com.cmc.board.domain.exception;

import static com.cmc.board.domain.constants.BookmarkExceptionStatus.BOOKMARK_DUPLICATED;

import com.cmc.global.common.exception.client.BadRequestException;

public class DuplicatedBookmarkException extends BadRequestException {
    public DuplicatedBookmarkException() {
        super(BOOKMARK_DUPLICATED);
    }
}
