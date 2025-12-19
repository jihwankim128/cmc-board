package com.cmc.board.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.board.domain.constants.BoardExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void 카테고리_이름이_주어지면_팩토리_메서드로_카테고리를_생성_할_수_있다() {
        // given
        String categoryName = "1번 카테고리";

        // when
        Category category = Category.from(categoryName);

        // then
        assertThat(category.getName()).isEqualTo(categoryName);
    }

    @Test
    void 팩토리_메서드_호출시_이름이_유효하지_않으면_예외가_전파된다() {
        // given
        String invalidName = "A".repeat(21);

        // when & then
        assertThatThrownBy(() -> Category.from(invalidName))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", BoardExceptionStatus.CATEGORY_NAME_TOO_LONG);
    }
}