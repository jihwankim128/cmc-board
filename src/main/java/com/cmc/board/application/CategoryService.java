package com.cmc.board.application;

import com.cmc.board.application.port.in.CreateCategoryUseCase;
import com.cmc.board.application.port.in.UpdateCategoryUseCase;
import com.cmc.board.application.port.in.command.UpdateCategoryCommand;
import com.cmc.board.domain.category.Category;
import com.cmc.board.domain.category.CategoryRepository;
import com.cmc.board.domain.category.vo.CategoryName;
import com.cmc.board.domain.constants.CategoryExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;
import com.cmc.global.common.exception.client.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements CreateCategoryUseCase, UpdateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public Long create(String name) {
        validateDuplicate(name);
        Category category = Category.from(new CategoryName(name));
        Category savedCategory = categoryRepository.save(category);
        return savedCategory.getId();
    }

    @Override
    public void update(UpdateCategoryCommand command) {
        Category category = getOrElseThrow(command);
        validateDuplicate(command.name(), command.id());

        category.update(command.toCategoryName());
        categoryRepository.save(category);
    }

    private Category getOrElseThrow(UpdateCategoryCommand command) {
        return categoryRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException(CategoryExceptionStatus.CATEGORY_NOT_FOUND));
    }

    private void validateDuplicate(String name, Long id) {
        if (categoryRepository.existsByNameExcludeId(name, id)) {
            throw new BadRequestException(CategoryExceptionStatus.CATEGORY_NAME_DUPLICATED);
        }
    }

    private void validateDuplicate(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new BadRequestException(CategoryExceptionStatus.CATEGORY_NAME_DUPLICATED);
        }
    }
}
