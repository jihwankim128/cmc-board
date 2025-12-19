package com.cmc.board.application;

import com.cmc.board.application.port.in.CreateCategoryUseCase;
import com.cmc.board.domain.Category;
import com.cmc.board.domain.CategoryRepository;
import com.cmc.board.domain.constants.BoardExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public Long create(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new BadRequestException(BoardExceptionStatus.CATEGORY_NAME_DUPLICATED);
        }
        Category category = categoryRepository.save(Category.from(name));
        return category.getId();
    }
}
