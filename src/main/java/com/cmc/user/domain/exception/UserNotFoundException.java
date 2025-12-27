package com.cmc.user.domain.exception;

import static com.cmc.user.domain.constants.UserExceptionStatus.USER_NOT_FOUND;

import com.cmc.global.common.exception.client.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}
