package com.cmc.global.web;

import com.cmc.global.common.base.StatusBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessStatus implements StatusBase {
    CREATED(201, "S001", "데이터 생성 성공"),
    READ(200, "S002", "데이터 조회 성공"),
    UPDATE(200, "S003", "데이터 수정 성공"),
    DELETE(200, "S004", "데이터 삭제 성공"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
