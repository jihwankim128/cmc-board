package com.cmc.global.auth;

import com.cmc.global.common.interfaces.StatusCode;

public enum AuthExceptionStatus implements StatusCode {
    UN_AUTHENTICATED,
    ADMIN_AUTHORITY
}
