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

    public static boolean isUnderMin(String value, int limit) {
        return !isBlank(value) && value.length() < limit;
    }

    public static boolean isOutOfRange(String value, int minLimit, int maxLimit) {
        return !isBlank(value) && (value.length() < minLimit || value.length() > maxLimit);
    }
}
