package com.cmc.global.auth;

import com.cmc.global.common.interfaces.StatusCode;

public enum AuthExceptionStatus implements StatusCode {
    UN_AUTHENTICATED,
    HAS_NOT_AUTHORITY
}
