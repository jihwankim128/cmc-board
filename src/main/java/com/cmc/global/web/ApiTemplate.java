package com.cmc.global.web;

import com.cmc.global.common.base.StatusBase;

public record ApiTemplate<T>(
        int status,
        String code,
        String message,
        T data
) {
    public static <T> ApiTemplate<T> of(StatusBase status, T data) {
        return new ApiTemplate<>(status.getStatus(), status.getCode(), status.getMessage(), data);
    }

    public static <T> ApiTemplate<T> from(StatusBase status) {
        return new ApiTemplate<>(status.getStatus(), status.getCode(), status.getMessage(), null);
    }
}
