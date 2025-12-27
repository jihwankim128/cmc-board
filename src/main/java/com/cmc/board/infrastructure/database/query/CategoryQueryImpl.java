package com.cmc.board.infrastructure.database.query;

import com.cmc.board.domain.exception.CategoryNotFoundException;
import com.cmc.board.infrastructure.database.jpa.CategoryJpaRepository;
import com.cmc.board.presentation.query.CategoryQuery;
import com.cmc.board.presentation.query.dto.CategoryDto;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryQueryImpl implements CategoryQuery {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<CategoryDto> getCategories() {
        return categoryJpaRepository.findAll()
                .stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .toList();
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        return categoryJpaRepository.findById(categoryId)
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public Map<Long, CategoryDto> getCategories(List<Long> categoryIds) {
        return categoryJpaRepository.findAllById(categoryIds)
                .stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .collect(Collectors.toMap(CategoryDto::id, Function.identity()));
    }
}
