package com.cmc.global.common.dto;

import com.cmc.global.common.interfaces.StatusCode;
import java.time.Instant;

public record CommonResponse<T>(
        String code,
        String message,
        T data,
        Instant timestamp
) {
    public static <T> CommonResponse<T> ok(T data, StatusCode code, String message) {
        return new CommonResponse<>(code.getCode(), message, data, Instant.now());
    }

    public static <Void> CommonResponse<Void> noContent(StatusCode code, String message) {
        return new CommonResponse<>(code.getCode(), message, null, Instant.now());
    }
}
