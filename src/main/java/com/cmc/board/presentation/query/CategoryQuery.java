package com.cmc.board.presentation.query;

import com.cmc.board.presentation.query.dto.CategoryDto;
import java.util.List;

public interface CategoryQuery {

    List<CategoryDto> getCategories();
}
