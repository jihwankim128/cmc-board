package com.cmc.global.web;

import com.cmc.global.common.base.StatusBase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalExceptionStatus implements StatusBase {
    ARGUMENT_NOT_VALID(400, "CMN-001", "응답 데이터의 유효성 검증이 실패했습니다."),
    ARGUMENT_TYPE_MISMATCH(400, "CMN-002", "경로 변수 또는 쿼리 파라미터의 타입이 잘못되었습니다."),
    MISSING_PARAMETER(400, "CMN-003", "필수 쿼리 파라미터가 누락되었습니다."),
    DATA_NOT_READABLE(400, "CMN-004", "읽을 수 없는 응답 데이터입니다."),
    NO_RESOURCE(404, "CMN-005", "존재하지 않는 경로입니다."),
    INTERNAL_SERVER_ERROR(500, "CMN-006", "알 수 없는 서버 에러가 발생했습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
