package com.cmc.board.domain;

public interface CategoryRepository {

    boolean existsByName(String name);

    Category save(Category category);
}
