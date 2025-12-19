package com.cmc.global.common.exception.client;

import com.cmc.global.common.exception.BaseException;
import com.cmc.global.common.interfaces.StatusCode;

public class ForbiddenException extends BaseException {

    public ForbiddenException(StatusCode status) {
        super(status);
    }
}
