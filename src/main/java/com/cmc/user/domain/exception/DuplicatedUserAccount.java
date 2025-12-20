package com.cmc.user.domain.exception;

import static com.cmc.user.domain.constants.UserExceptionStatus.USER_ACCOUNT_DUPLICATED;

import com.cmc.global.common.exception.client.BadRequestException;

public class DuplicatedUserAccount extends BadRequestException {

    public DuplicatedUserAccount() {
        super(USER_ACCOUNT_DUPLICATED);
    }
}
