package com.cmc.board.presentation.api.query;

import com.cmc.board.presentation.api.dto.category.CategoryDto;
import java.util.List;

public interface CategoryQuery {

    List<CategoryDto> getCategories();
}
