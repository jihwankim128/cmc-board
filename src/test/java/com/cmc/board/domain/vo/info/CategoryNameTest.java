package com.cmc.board.domain.vo.info;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

import com.cmc.board.domain.constants.BoardExceptionStatus;
import com.cmc.global.common.exception.client.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CategoryNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 카테고리_이름이_비어있으면_예외가_발생한다(String emptyCategoryName) {
        // when & then
        assertThatThrownBy(() -> new CategoryName(emptyCategoryName))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", BoardExceptionStatus.CATEGORY_NAME_EMPTY);
    }

    @Test
    void 카테고리_이름이_20자를_초과하면_예외가_발생한다() {
        String tooLongName = "A".repeat(21);

        assertThatThrownBy(() -> new CategoryName(tooLongName))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", BoardExceptionStatus.CATEGORY_NAME_TOO_LONG);
    }

    @Test
    void 유효한_카테고리_이름은_성공적으로_객체를_생성한다() {
        // given
        String validName = "유효한이름";

        // when & then
        // ⭐️ 예외가 발생하지 않음을 명시적으로 검증 (테스트 커버리지와 신뢰성 향상)
        assertThatNoException().isThrownBy(() -> {
            CategoryName categoryName = new CategoryName(validName);
            assertThat(categoryName.value()).isEqualTo(validName);
        });
    }
}
