package com.cmc.global.common.exception.client;

import com.cmc.global.common.exception.BaseException;
import com.cmc.global.common.interfaces.StatusCode;

public class NotFoundException extends BaseException {

    public NotFoundException(StatusCode status) {
        super(status);
    }
}
