package com.cmc.global.common.exception;

import com.cmc.global.common.base.StatusBase;

public class BusinessException extends RuntimeException {

    private final StatusBase status;

    public BusinessException(StatusBase status) {
        super(status.getMessage());
        this.status = status;
    }

    public StatusBase getStatus() {
        return status;
    }
}
