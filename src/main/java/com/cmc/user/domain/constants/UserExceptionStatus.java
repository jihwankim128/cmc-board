package com.cmc.user.domain.constants;

import com.cmc.global.common.interfaces.StatusCode;

public enum UserExceptionStatus implements StatusCode {
    USER_NICKNAME_EMPTY,
    USER_NICKNAME_OUT_OF_RANGE,
    USER_EMAIL_INVALID,
}
