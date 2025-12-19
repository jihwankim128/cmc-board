package com.cmc.global.common.exception;

import com.cmc.global.common.interfaces.StatusCode;

public class BaseException extends RuntimeException {
    private final StatusCode status;
    private final Object[] args;

    public BaseException(StatusCode status) {
        super(status.getCode());
        this.status = status;
        this.args = new Object[]{};
    }

    // 디버깅 메시지용
    public BaseException(StatusCode status, String message) {
        super(message);
        this.status = status;
        this.args = new Object[]{};
    }

    public BaseException(StatusCode status, Object... args) {
        super(status.getCode());
        this.status = status;
        this.args = args;
    }

    public BaseException(StatusCode status, String message, Object... args) {
        super(message);
        this.status = status;
        this.args = args;
    }

    public StatusCode getStatus() {
        return status;
    }

    public Object[] getArgs() {
        return args;
    }
}
