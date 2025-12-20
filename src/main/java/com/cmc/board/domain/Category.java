package com.cmc.board.domain;

import com.cmc.board.domain.vo.info.CategoryName;
import lombok.Getter;

@Getter
public class Category {

    private final Long id;
    private CategoryName name;

    private Category(CategoryName name) {
        this.id = null;
        this.name = name;
    }

    public static Category from(CategoryName name) {
        return new Category(name);
    }

    public String getName() {
        return name.value();
    }

    public void update(CategoryName name) {
        this.name = name;
    }
}
