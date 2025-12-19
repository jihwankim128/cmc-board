package com.cmc.global.common.dto;

public record ErrorDetail(
        String field,
        String reason
) {
}
