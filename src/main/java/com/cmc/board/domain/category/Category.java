package com.cmc.board.domain.category;

import com.cmc.board.domain.category.vo.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class Category {

    private final Long id;
    private CategoryName name;

    public static Category from(CategoryName name) {
        return new Category(null, name);
    }

    public String getName() {
        return name.value();
    }

    public void update(CategoryName name) {
        this.name = name;
    }
}
