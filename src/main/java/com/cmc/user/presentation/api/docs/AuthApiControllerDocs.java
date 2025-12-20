package com.cmc.user.presentation.api.docs;

import com.cmc.board.presentation.api.dto.bookmark.CreateBookmarkDto;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.common.dto.ErrorResponse;
import com.cmc.global.docs.CommonDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "사용자 인증 API", description = "인증 관련 API 목록입니다.")
public interface AuthApiControllerDocs {

    @Operation(summary = "회원가입", description = "회원 가입을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "USER_SIGNUP_SUCCESS")
    @ApiResponse(
            responseCode = "400",
            description = """
                        USER_NICKNAME_EMPTY: 사용자 닉네임 정보가 비어있는 경우
                        USER_NICKNAME_OUT_OF_RANGE: 사용자 닉네임 길이 범위를 초과한 경우
                        USER_EMAIL_INVALID: 사용자 이메일 형식이 잘못된 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @CommonDocs
    CommonResponse<Void> createBookmark(CreateBookmarkDto dto);
}
