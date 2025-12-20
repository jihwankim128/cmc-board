package com.cmc.board.application.port.in.command;

import com.cmc.board.domain.category.vo.CategoryName;

public record UpdateCategoryCommand(Long id, String name) {

    public CategoryName toCategoryName() {
        return new CategoryName(name);
    }
}
