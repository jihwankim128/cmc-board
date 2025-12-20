package com.cmc.board.domain.category;

import static org.assertj.core.api.Assertions.assertThat;

<<<<<<< HEAD
import com.cmc.board.domain.category.vo.CategoryName;
=======
>>>>>>> c2c61da (feat: add board post model)
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void 카테고리_이름이_주어지면_팩토리_메서드로_카테고리를_생성_할_수_있다() {
        // given
        CategoryName categoryName = new CategoryName("1번 카테고리");

        // when
        Category category = Category.from(categoryName);

        // then
        assertThat(category.getName()).isEqualTo(categoryName.value());
    }

    @Test
    void 카테고리_수정이_된다() {
        // given
        Category category = Category.from(new CategoryName("기존 카테고리"));
        CategoryName newCategoryName = new CategoryName("새로운 카테고리");

        // when
        category.update(newCategoryName);

        // then
        assertThat(category.getName()).isEqualTo(newCategoryName.value());
    }
}