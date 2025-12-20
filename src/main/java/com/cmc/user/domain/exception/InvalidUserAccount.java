package com.cmc.user.domain.exception;

import static com.cmc.user.domain.constants.UserExceptionStatus.USER_ACCOUNT_INVALID;

import com.cmc.global.common.exception.client.BadRequestException;

public class InvalidUserAccount extends BadRequestException {

    public InvalidUserAccount() {
        super(USER_ACCOUNT_INVALID);
    }
}
