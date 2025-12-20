package com.cmc.board.presentation.api.docs;

import com.cmc.board.presentation.api.dto.category.CreateCategoryDto;
import com.cmc.board.presentation.api.dto.category.UpdateCategoryDto;
import com.cmc.board.presentation.query.dto.CategoryDto;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.common.dto.ErrorResponse;
import com.cmc.global.docs.CommonDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "게시판 카테고리 API", description = "카테고리 관련 API 목록입니다.")
public interface CategoryApiControllerDocs {

    @Operation(summary = "카테고리 생성", description = "카테고리를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "CREATE_CATEGORY_SUCCESS")
    @ApiResponse(
            responseCode = "400",
            description = """
                    CATEGORY_NAME_EMPTY: 카테고리 명이 비어있을 경우
                    CATEGORY_NAME_TOO_LONG: 카테고리 명이 너무 긴 경우
                    CATEGORY_NAME_DUPLICATED: 중복된 카테고리 명이 있는 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @CommonDocs
    CommonResponse<Long> create(CreateCategoryDto dto);


    @Operation(summary = "카테고리 수정", description = "카테고리를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "UPDATE_CATEGORY_SUCCESS")
    @ApiResponse(
            responseCode = "400",
            description = """
                    CATEGORY_NAME_EMPTY: 카테고리 명이 비어있을 경우
                    CATEGORY_NAME_TOO_LONG: 카테고리 명이 너무 긴 경우
                    CATEGORY_NAME_DUPLICATED: 중복된 카테고리 명이 있는 경우
                    CATEGORY_NOT_FOUND: 기존 카테고리를 찾을 수 없는 경우
                    """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @CommonDocs
    CommonResponse<Void> update(Long categoryId, UpdateCategoryDto dto);


    @Operation(summary = "카테고리 조회", description = "카테고리를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "GET_CATEGORIES_SUCCESS")
    @CommonDocs
    CommonResponse<List<CategoryDto>> getCategories();
}
