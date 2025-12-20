package com.cmc.board.domain.exception;

import com.cmc.board.domain.constants.PostExceptionStatus;
import com.cmc.global.common.exception.client.NotFoundException;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException() {
        super(PostExceptionStatus.NOT_FOUND_POST);
    }
}
