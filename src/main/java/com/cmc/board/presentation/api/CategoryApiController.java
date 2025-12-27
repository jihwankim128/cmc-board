package com.cmc.board.presentation.api;

import static com.cmc.board.presentation.api.status.CategorySuccessStatus.CREATE_CATEGORY_SUCCESS;
import static com.cmc.board.presentation.api.status.CategorySuccessStatus.GET_CATEGORIES_SUCCESS;
import static com.cmc.board.presentation.api.status.CategorySuccessStatus.UPDATE_CATEGORY_SUCCESS;

import com.cmc.board.application.port.in.CreateCategoryUseCase;
import com.cmc.board.application.port.in.UpdateCategoryUseCase;
import com.cmc.board.presentation.api.docs.CategoryApiControllerDocs;
import com.cmc.board.presentation.api.dto.category.CreateCategoryDto;
import com.cmc.board.presentation.api.dto.category.UpdateCategoryDto;
import com.cmc.board.presentation.query.CategoryQuery;
import com.cmc.board.presentation.query.dto.CategoryDto;
import com.cmc.global.auth.annotation.AuthRole;
import com.cmc.global.auth.annotation.PreAuth;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.web.message.MessageSourceHelper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController implements CategoryApiControllerDocs {

    private final CreateCategoryUseCase createUseCase;
    private final UpdateCategoryUseCase updateUseCase;
    private final CategoryQuery categoryQuery;
    private final MessageSourceHelper messageSourceHelper;

    @PostMapping
    @PreAuth(value = AuthRole.ADMIN)
    public CommonResponse<Long> create(@RequestBody @Valid CreateCategoryDto dto) {
        String message = messageSourceHelper.extractMessage(CREATE_CATEGORY_SUCCESS);
        Long result = createUseCase.create(dto.name());
        return CommonResponse.ok(result, CREATE_CATEGORY_SUCCESS, message);
    }

    @PutMapping("/{categoryId}")
    @PreAuth(value = AuthRole.ADMIN)
    public CommonResponse<Void> update(@PathVariable Long categoryId, @RequestBody @Valid UpdateCategoryDto dto) {
        String message = messageSourceHelper.extractMessage(UPDATE_CATEGORY_SUCCESS);
        updateUseCase.update(dto.toCommand(categoryId));
        return CommonResponse.noContent(UPDATE_CATEGORY_SUCCESS, message);
    }

    @GetMapping
    @PreAuth(value = AuthRole.ANONYMOUS)
    public CommonResponse<List<CategoryDto>> getCategories() {
        String message = messageSourceHelper.extractMessage(GET_CATEGORIES_SUCCESS);
        List<CategoryDto> result = categoryQuery.getCategories();
        return CommonResponse.ok(result, GET_CATEGORIES_SUCCESS, message);
    }
}
