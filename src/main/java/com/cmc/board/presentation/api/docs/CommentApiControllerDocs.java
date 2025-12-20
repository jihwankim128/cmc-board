package com.cmc.board.presentation.api.docs;

import com.cmc.board.presentation.api.dto.comment.ReplyCommentDto;
import com.cmc.board.presentation.api.dto.comment.UpdateCommentDto;
import com.cmc.board.presentation.api.dto.comment.WriteCommentDto;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.common.dto.ErrorResponse;
import com.cmc.global.docs.CommonDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "게시판 댓글 API", description = "댓글 관련 API 목록입니다.")
public interface CommentApiControllerDocs {

    @Operation(summary = "댓글 생성", description = "댓글을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "CREATE_COMMENT_SUCCESS")
    @ApiResponse(
            responseCode = "400",
            description = """
                        COMMENT_CONTENT_BLANK: 댓글 내용이 빈 경우
                        COMMENT_CONTENT_TOO_LONG: 댓글 내용이 최대 길이를 초과한 경우
                        COMMENT_POST_NOT_NULL: 댓글의 게시글 정보가 필요합니다.
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(
            responseCode = "404",
            description = """
                        POST_NOT_FOUND: 게시글이 없는 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @CommonDocs
    CommonResponse<Long> create(WriteCommentDto dto);

    @Operation(summary = "대댓글 생성", description = "대댓글을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "CREATE_REPLY_SUCCESS")
    @ApiResponse(
            responseCode = "400",
            description = """
                        COMMENT_CONTENT_BLANK: 댓글 내용이 빈 경우
                        COMMENT_CONTENT_TOO_LONG: 댓글 내용이 최대 길이를 초과한 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(
            responseCode = "404",
            description = """
                        POST_NOT_FOUND: 게시글이 없는 경우
                        COMMENT_NOT_FOUND: 부모 댓글이 없는 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @CommonDocs
    CommonResponse<Long> reply(Long parentId, ReplyCommentDto dto);

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "UPDATE_COMMENT_SUCCESS")
    @ApiResponse(
            responseCode = "400",
            description = """
                        COMMENT_CONTENT_BLANK: 댓글 내용이 빈 경우
                        COMMENT_CONTENT_TOO_LONG: 댓글 내용이 최대 길이를 초과한 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(
            responseCode = "401",
            description = """
                        MISMATCH_COMMENT_AUTHOR: 작성자가 아닌 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(
            responseCode = "404",
            description = """
                        COMMENT_NOT_FOUND: 댓글이 없는 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @CommonDocs
    CommonResponse<Void> update(Long commentId, UpdateCommentDto dto);
}
