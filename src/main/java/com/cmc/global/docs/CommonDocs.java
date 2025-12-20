package com.cmc.global.docs;

import static java.lang.annotation.ElementType.METHOD;

import com.cmc.global.common.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = """
                                공통 요청 예외입니다.
                                REQUEST_FIELD_NOT_VALID : 요청 필드 유효성 검증에 실패했습니다.
                                REQUEST_PARAM_NOT_VALID : 요청 파라미터 유효성 검증에 실패했습니다.
                                REQUEST_NOT_READABLE : 요청 데이터를 읽을 수 없습니다.
                                REQUEST_ARGUMENT_TYPE_MISMATCH : 요청 파라미터 타입이 불일치합니다.
                                REQUEST_MISSING_PARAMETER : 필수 요청 파라미터가 누락되었습니다.
                        """,
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
                responseCode = "500",
                description = """
                                서버 예외입니다.
                                INTERNAL_SERVER_ERROR : 알 수 없는 서버 예외입니다.
                        """,
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
})
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CommonDocs {
}
