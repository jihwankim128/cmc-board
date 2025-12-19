package com.cmc.global.common.exception.client;

import com.cmc.global.common.exception.BaseException;
import com.cmc.global.common.interfaces.StatusCode;

public class BadRequestException extends BaseException {

    public BadRequestException(StatusCode status) {
        super(status);
    }
}
