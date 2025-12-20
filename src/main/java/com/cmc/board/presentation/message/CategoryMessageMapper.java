package com.cmc.board.presentation.message;

import com.cmc.board.domain.constants.CategoryExceptionStatus;
import com.cmc.board.presentation.api.status.CategorySuccessStatus;
import com.cmc.global.common.interfaces.StatusCode;
import com.cmc.global.web.message.MessageKeyMapper;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CategoryMessageMapper implements MessageKeyMapper {
    @Override
    public Map<StatusCode, String> getMessageKeys() {
        return Map.of(
                CategorySuccessStatus.CREATE_CATEGORY_SUCCESS, "category.create.success",
                CategorySuccessStatus.UPDATE_CATEGORY_SUCCESS, "category.update.success",
                CategorySuccessStatus.GET_CATEGORIES_SUCCESS, "category.get.categories",
                CategoryExceptionStatus.CATEGORY_NAME_EMPTY, "category.name.empty",
                CategoryExceptionStatus.CATEGORY_NAME_TOO_LONG, "category.name.tooLong",
                CategoryExceptionStatus.CATEGORY_NAME_DUPLICATED, "category.name.duplicated",
                CategoryExceptionStatus.CATEGORY_NOT_FOUND, "category.not.found"
        );
    }
}
