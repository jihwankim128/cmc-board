package com.cmc.board.domain.category;

import java.util.Optional;

public interface CategoryRepository {

    boolean existsByName(String name);

    Category save(Category category);

    Optional<Category> findById(Long id);

    boolean existsByNameExcludeId(String name, Long id);
}
