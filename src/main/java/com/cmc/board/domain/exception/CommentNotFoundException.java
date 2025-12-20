package com.cmc.board.domain.exception;

import com.cmc.board.domain.constants.CommentExceptionStatus;
import com.cmc.global.common.exception.client.NotFoundException;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException() {
        super(CommentExceptionStatus.NOT_FOUND_COMMENT);
    }
}
