package com.cmc.global.common.utils;

public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isOverLimit(String value, int limit) {
        return !isBlank(value) && value.length() >= limit;
    }
}
