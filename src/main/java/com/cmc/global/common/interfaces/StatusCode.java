package com.cmc.global.common.interfaces;

public interface StatusCode {
    default String getCode() {
        return name();
    }

    String name();
}