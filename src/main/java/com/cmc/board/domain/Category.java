package com.cmc.board.domain;

import com.cmc.board.domain.vo.info.CategoryName;
import lombok.Getter;

@Getter
public class Category {

    private final Long id;
    private final CategoryName name;

    private Category(CategoryName name) {
        this.id = null;
        this.name = name;
    }

    public static Category from(String name) {
        return new Category(new CategoryName(name));
    }

    public String getName() {
        return name.value();
    }
}
