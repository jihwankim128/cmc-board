package com.cmc.board.presentation.api;

import static com.cmc.board.presentation.api.status.CategorySuccessStatus.CREATE_CATEGORY_SUCCESS;
import static com.cmc.board.presentation.api.status.CategorySuccessStatus.UPDATE_CATEGORY_SUCCESS;

import com.cmc.board.application.port.in.CreateCategoryUseCase;
import com.cmc.board.application.port.in.UpdateCategoryUseCase;
import com.cmc.board.presentation.api.dto.CreateCategoryDto;
import com.cmc.board.presentation.api.dto.UpdateCategoryDto;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.web.message.MessageSourceHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryApiController {

    private final CreateCategoryUseCase createUseCase;
    private final UpdateCategoryUseCase updateUseCase;
    private final MessageSourceHelper messageSourceHelper;

    @PostMapping
    public CommonResponse<Long> create(@RequestBody @Valid CreateCategoryDto dto) {
        String message = messageSourceHelper.extractMessage(CREATE_CATEGORY_SUCCESS);
        Long result = createUseCase.create(dto.name());
        return CommonResponse.ok(result, CREATE_CATEGORY_SUCCESS, message);
    }

    @PutMapping("/{categoryId}")
    public CommonResponse<Void> update(@PathVariable Long categoryId, @RequestBody @Valid UpdateCategoryDto dto) {
        String message = messageSourceHelper.extractMessage(UPDATE_CATEGORY_SUCCESS);
        updateUseCase.update(dto.toCommand(categoryId));
        return CommonResponse.noContent(UPDATE_CATEGORY_SUCCESS, message);
    }
}
