package com.cmc.board.presentation.api.docs;

import com.cmc.board.presentation.api.dto.bookmark.CreateBookmarkDto;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.common.dto.ErrorResponse;
import com.cmc.global.docs.CommonDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "게시판 북마크 API", description = "북마크 관련 API 목록입니다.")
public interface BookmarkApiControllerDocs {

    @Operation(summary = "북마크 생성", description = "북마크를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "CREATE_BOOKMARK_SUCCESS")
    @ApiResponse(
            responseCode = "400",
            description = """
                        BOOKMARK_REQUIRED_POST_ID: 북마크할 게시글 정보가 누락
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(
            responseCode = "404",
            description = """
                        POST_NOT_FOUND: 북마크할 게시글 정보가 없는 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @CommonDocs
    CommonResponse<Void> createBookmark(CreateBookmarkDto dto, Long userId);
}
