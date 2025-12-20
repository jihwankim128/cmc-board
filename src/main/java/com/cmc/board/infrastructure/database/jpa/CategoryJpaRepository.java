package com.cmc.board.infrastructure.database.jpa;

import com.cmc.board.infrastructure.database.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdIsNot(String name, Long id);
}
