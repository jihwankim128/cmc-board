package com.cmc.board.infrastructure.database;

import com.cmc.board.domain.Category;
import com.cmc.board.domain.CategoryRepository;
import com.cmc.board.infrastructure.database.jpa.CategoryJpaRepository;
import com.cmc.board.infrastructure.database.jpa.entity.CategoryEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = jpaRepository.save(CategoryEntity.from(category));
        return categoryEntity.toDomain();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return jpaRepository.findById(id)
                .map(CategoryEntity::toDomain);
    }

    @Override
    public boolean existsByNameExcludeId(String name, Long id) {
        return jpaRepository.existsByNameAndIdIsNot(name, id);
    }
}
