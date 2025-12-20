package com.cmc.board.presentation.api.docs;

import com.cmc.board.presentation.api.dto.post.CreatePostDto;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.common.dto.ErrorResponse;
import com.cmc.global.docs.CommonDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "게시판 게시글 API", description = "게시글 관련 API 목록입니다.")
public interface PostApiControllerDocs {

    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "CREATE_POST_SUCCESS")
    @ApiResponse(
            responseCode = "400",
            description = """
                        POST_TITLE_BLANK: 게시글 제목이 빈 경우
                        POST_TITLE_TOO_LONG: 게시글 제목 길이 초과
                        POST_CONTENT_BLANK: 게시글 내용이 빈 경우
                        POST_CONTENT_TOO_SHORT: 게시글 내용 길이 부족
                        NOT_FOUND_CATEGORY: 선택 카테고리가 없는 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @CommonDocs
    CommonResponse<Long> create(CreatePostDto dto);
}
