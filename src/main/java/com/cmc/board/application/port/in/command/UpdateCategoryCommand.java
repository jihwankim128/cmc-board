package com.cmc.board.application.port.in.command;

import com.cmc.board.domain.vo.info.CategoryName;

public record UpdateCategoryCommand(Long id, String name) {

    public CategoryName toCategoryName() {
        return new CategoryName(name);
    }
}
