package com.cmc.global.common.dto;

import java.time.Instant;

public record CommonResponse<T>(
        String message,
        T data,
        Instant timestamp
) {
    public static <T> CommonResponse<T> ok(T data, String message) {
        return new CommonResponse<>(message, data, Instant.now());
    }
}
