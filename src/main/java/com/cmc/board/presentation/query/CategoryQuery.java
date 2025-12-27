package com.cmc.board.presentation.query;

import com.cmc.board.presentation.query.dto.CategoryDto;
import java.util.List;
import java.util.Map;

public interface CategoryQuery {

    List<CategoryDto> getCategories();

    CategoryDto getCategory(Long categoryId);

    Map<Long, CategoryDto> getCategories(List<Long> categoryIds);
}
