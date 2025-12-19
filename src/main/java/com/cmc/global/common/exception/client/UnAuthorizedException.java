package com.cmc.global.common.exception.client;

import com.cmc.global.common.exception.BaseException;
import com.cmc.global.common.interfaces.StatusCode;

public class UnAuthorizedException extends BaseException {

    public UnAuthorizedException(StatusCode status) {
        super(status);
    }
}
